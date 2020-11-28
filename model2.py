#!/usr/bin/env python

import pandas as pd
import numpy as np
import string
import nltk
import csv
import sys
import re

from sklearn.neural_network import MLPClassifier
from sklearn.neighbors import KNeighborsClassifier

from joblib import dump, load

def preprocess(text):
    pattern = re.compile('[\W_]+')
    lowercase = text.lower()
    lowercase = lowercase.replace('\\n', ' ')
    lowercase = pattern.sub(' ', lowercase)
    lcp = "".join([char for char in lowercase if char not in string.punctuation])
    words = nltk.tokenize.word_tokenize(lcp)
    words = [word for word in words if word not in nltk.corpus.stopwords.words('english')]
    words_text = [word for word in words if len(word) <= 15 and len(word)>=3]
    txt = " ".join(words)
    return txt

def read_glove_vecs(glove_file):
    with open(glove_file, 'r', encoding="utf8") as f:
        word_to_vec_map = {}
        for line in f:
            line = line.strip().split()
            curr_word = line[0]
            #if curr_word in words_text:
            word_to_vec_map[curr_word] = np.array(line[1:], dtype=np.float64)
    return word_to_vec_map

def sentence_to_avg(sentence, word_to_vec_map):
    words = sentence.split()
    if len(words) == 0: return np.zeros(300)
    avg = np.zeros(300)
    total = 0
    for w in words:
        if w in word_to_vec_map:
            total += word_to_vec_map[w]
    avg = total / len(words)
    return avg

clf = load('mlp.joblib')

print("READING GLOVE FILE, COULD TAKE SEVERAL MINUTES...", flush=True)
word_to_vec_map = read_glove_vecs('../data/glove.42B.300d.txt')
print("GLOVE FILE READING DONE!", flush=True)

while True:
    text = sys.stdin.readline()
    text = preprocess(text)
    txt_glove = sentence_to_avg(text, word_to_vec_map)
    if type(txt_glove) == float: txt_glove = np.zeros((300,))
    rating = clf.predict([txt_glove])
    print(rating[0], flush=True)
