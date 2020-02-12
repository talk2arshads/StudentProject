package com.student2course.springboot.service;

@FunctionalInterface
public interface StudentServiceInterface {
	
	abstract void getServices();
	
	static String getNewJavaFeatures() {

			/*Lambda Expressions − a new language feature allowing treating actions as objects
			Optional − special wrapper class used for expressing optionality
			Functional Interface – an interface with maximum one abstract method, implementation can be provided using a Lambda Expression
			Default methods − give us the ability to add full implementations in interfaces besides abstract methods
			Stream API − a special iterator class that allows processing collections of objects in a functional manner
			Date API − an improved, immutable JodaTime-inspired Date API*/
		return "string";
	}
	
	

}
