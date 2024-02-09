package com.example.services_novigrad.Services;

public class ServiceList {

    private String servicename;
    private String formulaires;
    private String documents;

    private String id;

    //String docId;

    public ServiceList(){
        //empty constructor needed
    }

    public ServiceList(String servicename, String formulaires, String documents){
        this.servicename = servicename;
        this.formulaires = formulaires;
        this.documents = documents;

    }

    public ServiceList(String servicename, String formulaires, String documents, String id){
        this.servicename = servicename;
        this.formulaires = formulaires;
        this.documents = documents;
        this.id = id;


    }

    public String getServicename() {
        return servicename;
    }

    public String getFormulaires() {
        return formulaires;
    }

    public String getDocuments() {
        return documents;
    }

    public String getId() {
        return id;
    }


    public void setServicename(String servicename){
        this.servicename = servicename;
    };

    public void setFormulaires(String formulaires){
        this.formulaires = formulaires;
    };

    public void setDocuments(String documents){
        this.documents = documents;
    };

    public void setId() {
        this.id = id;
        //return null;
    }
}
