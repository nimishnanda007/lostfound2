package com.example.lostfound2;

public class Item {
    private final int id;
    private final String personName;
    private final String phone;
    private final String description;
    private final String date;
    private final String status;
    private final double latitude;
    private final double longitude;

    public Item(int id, String personName, String phone,
                String description, String date, String status,
                double latitude, double longitude) {
        this.id = id;
        this.personName = personName;
        this.phone = phone;
        this.description = description;
        this.date = date;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() { return id; }
    public String getPersonName() { return personName; }
    public String getPhone() { return phone; }
    public String getDescription() { return description; }
    public String getDate() { return date; }
    public String getStatus() { return status; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
}
