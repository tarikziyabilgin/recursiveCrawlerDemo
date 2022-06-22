package com.bilgin;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;

public class ConnectionResult {

    public ConnectionResult(){

    }

    public ConnectionResult(Connection connection, Document document){
        this.connection = connection;
        this.document = document;
    }
    private Document document;
    private Connection connection;

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
