package com.techelevator.model;

import java.util.Objects;

public class MenuItem {
    //Main|DisplayItems|en-US|Display Vending Machine Items|-|N
    private String menuCode;
    private String menuItemCode;
    private String menulanguage;
    private String menuText;
    private String subMenu;
    private Boolean hide;
    private Object menuObject;
    public MenuItem(){
        this.hide=false;
    }
    public MenuItem(Object object){
        String menuValue = object.toString();
        this.menuObject = object;
        this.hide = menuValue.contains("(hide)");
        if (this.hide){
            menuValue = menuValue.replace("(hide)","");
        }
        this.menuCode = menuValue;
        this.menuText = menuValue;
    }
    public MenuItem(String menuCode, String menuItemCode, String menulanguage, String menuText, String subMenu, Boolean hide) {
        this.menuCode = menuCode;
        this.menuItemCode = menuItemCode;
        this.menulanguage = menulanguage;
        this.menuText = menuText;
        this.subMenu = subMenu;
        this.hide = hide;
    }

    public Object getMenuObject() {
        return menuObject;
    }

    public void setMenuObject(Object menuObject) {
        this.menuObject = menuObject;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuItemCode() {
        return menuItemCode;
    }

    public void setMenuItemCode(String menuItemCode) {
        this.menuItemCode = menuItemCode;
    }

    public String getMenulanguage() {
        return menulanguage;
    }

    public void setMenulanguage(String menulanguage) {
        this.menulanguage = menulanguage;
    }

    public String getMenuText() {
        return menuText;
    }

    public void setMenuText(String menuText) {
        this.menuText = menuText;
    }

    public String getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(String subMenu) {
        this.subMenu = subMenu;
    }

    public Boolean getHide() {
        return hide;
    }

    public void setHide(Boolean hide) {
        this.hide = hide;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItem menuItem = (MenuItem) o;
        return menuCode.equals(menuItem.menuCode) && menuItemCode.equals(menuItem.menuItemCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuCode, menuItemCode);
    }

    @Override
    public String toString() {
        return menuText;
    }
}
