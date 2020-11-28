package com.example.demo;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

@SpringBootApplication
@RestController
public class DemoApplication {
	public static BufferedReader bfr;
	public static BufferedWriter bfw;
	public static void main(String[] args) throws IOException {
		ProcessBuilder pb = new ProcessBuilder("python","model2.py", "-u");
		pb.redirectErrorStream(true);
		Process p = pb.start();
		bfr = new BufferedReader(new InputStreamReader(p.getInputStream()));
		bfw = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
		System.out.println(bfr.readLine());
		System.out.println(bfr.readLine());
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping("/model")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) throws IOException, InterruptedException {
	    //Process p = Runtime.getRuntime().exec("python model.py " + name);
	    ProcessBuilder pb = new ProcessBuilder("python","model.py", name, "-u");
	    pb.redirectErrorStream(true);
	    Process p = pb.start();
        BufferedReader bfr = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = "";
        System.out.println("Running Python starts: " + line);
        int exitCode = p.waitFor();
        System.out.println("Exit Code : " + exitCode);
        line = bfr.readLine();
        System.out.println("Line: " + line);
		return String.format("Rating is %s \n", line);
	}
	
	@GetMapping(path="/model2", produces=MediaType.APPLICATION_JSON_VALUE)
	public String hello2(@RequestParam(value = "name", defaultValue = "World") String name) throws IOException, InterruptedException {
		bfw.write(name + "\n");
		bfw.flush();
		String line = "";
        //System.out.println(bfr.readLine());
        line = bfr.readLine();
        System.out.println("Model output is: " + line);
		return String.format("{\"rating\": %s}\n", line);
	}
	
	@GetMapping(path="/model3", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> hello3(@RequestParam(value = "name", defaultValue = "World") String name) throws IOException, InterruptedException {
		bfw.write(name + "\n");
		bfw.flush();
		String line = "";
        //System.out.println(bfr.readLine());
        line = bfr.readLine();
        System.out.println("Model output is: " + line);
		return new ResponseEntity<>(String.format("{\"rating\": %s}\n", line),  HttpStatus.OK);
	}
}

