package com.example.a_terzjan.login.orders;

/**
 * Created by a_terzjan on 05.07.2018.
 */

public class Orders {
    private String route;

    private String status;

    public Orders(String route, String status){
        this.route = route;

        this.status = status;
    }
    public String getStatus() {
        return this.status;
    }

    public void setRoute(String route){
        this.route = route;
    }
    public String getRoute(){
        return this.route;
    }
}