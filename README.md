#### Simple_Notes


#Simple Notes App

#Notes are a great way to keep track of information for yourself, but it also makes it easy to share information with others (like your grocery list with your husband!) Open a note you want to share and tap Share.


#This app must be built in Java and run on Marshmallow 6.0 (API level 23).
#Functionality:-
	1. Create a new to do item and edit an existing item and permanently remove an item
Todo: use of Recyclerview.. 

	a. Data must be saved into a sql database for offline usage

https://www.techypid.com/insert-and-display-data-in-recyclerview/(new room concept)

#dependencies {
    def room_version = "2.4.2"

    implementation "androidx.room:room-runtime:$room_version"
    annotationProcessor "androidx.room:room-compiler:$room_version"

}

#Difference between SQLite and Room persistence library:-

1.In case of SQLite, There is no compile time verification of raw SQLite queries. But in Room there is SQL validation at compile time.
2.As your schema changes, you need to update the affected SQL queries manually. Room solves this problem.
3.You need to use lots of boilerplate code to convert between SQL queries and Java data objects. But, Room maps our database objects to Java Object 4.without boilerplate code.
4.Room is built to work with LiveData and RxJava for data observation, while SQLite does not.


<!-- 
	2. Mark an existing item as done
	a. Show the user items still to do and items marked as done
	3. The to do item view must show the updated on date time formatted in the same style as Wed, Jul 4, &#39;01 12:08 PM
Add a location marker to a to do item
	a. The user must be able to drop a pin on a map view
	b. The user must be able see a the map marker of the location chosen in the to do items view
	c. The user must be able to edit the location
	d. The user must be able to remove the location from the to do item view
Add an image to the to do item
	a. The user must be able to open the camera or gallery application on their device and take an image
	b. The image must be shown as a thumbnail on the to do item view
	c. The image can be swapped by the user for a different one
	d. The user must be able to remove the image from the view -->
