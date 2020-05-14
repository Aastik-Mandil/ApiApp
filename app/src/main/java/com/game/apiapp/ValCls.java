package com.game.apiapp;


public class ValCls {
    String login, id, repository_url,state;

    public ValCls(String login, String id, String repository_url, String state) {
        this.login = login;
        this.id = id;
        this.repository_url = repository_url;
        this.state = state;
    }

    public String getLogin() { return login; }

    public void setLogin(String login) { this.login = login; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getRepository_url() { return repository_url; }

    public void setRepository_url(String repository_url) { this.repository_url = repository_url; }

    public String getState() { return state; }

    public void setState(String state) { this.state = state; }
}
