package com.tracker;

public class Contact {
	int id;
	String name;
	String date;
	int rating;
	byte[] photo;
	
	public Contact(int ID, String Name, String Date, int Rating, byte[] Photo)
	{
		id = ID;
		name = Name;
		date = Date;
		rating = Rating;
		photo = Photo;			
	}
}
