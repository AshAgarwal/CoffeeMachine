package com.coffeemachine;

import java.util.Scanner;

enum CoffeeType {
    ESPRESSO(250, 0, 16, 4),
    LATTE(350, 75, 20, 7),
    CAPPUCCINO(200, 100, 12, 6);

    private final int water;
    private final int milk;
    private final int coffeeBeans;
    private final int cost;

    CoffeeType (int water, int milk, int beans, int cost) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = beans;
        this.cost = cost;
    }

    public int getWater() {
        return water;
    }

    public int getMilk() {
        return milk;
    }

    public int getCoffeeBeans() {
        return coffeeBeans;
    }

    public int getCost() {
        return cost;
    }
}

enum CoffeeMachineState {
    IDLE,
    BUY,
    FILL,
    FILL_WATER,
    FILL_MILK,
    FILL_COFFEE_BEANS,
    FILL_CUPS,
    REMAINING,
    TAKE,
    EXIT;
}

class CoffeeMaker {
    int waterAmount;
    int milkAmount;
    int beansAmount;
    int disposableCups;
    int money;
    CoffeeMachineState state;

    CoffeeMaker () {
        // Initial Resources in CoffeeMaker Machine
        this.waterAmount = 400;
        this.milkAmount = 540;
        this.beansAmount = 120;
        this.disposableCups = 9;
        this.money = 550;
        this.state = CoffeeMachineState.IDLE;
    }

    public int getWaterAmount() {
        return waterAmount;
    }

    public void setWaterAmount(int waterAmount) {
        this.waterAmount = waterAmount;
    }

    public int getMilkAmount() {
        return milkAmount;
    }

    public void setMilkAmount(int milkAmount) {
        this.milkAmount = milkAmount;
    }

    public int getBeansAmount() {
        return beansAmount;
    }

    public void setBeansAmount(int beansAmount) {
        this.beansAmount = beansAmount;
    }

    public int getDisposableCups() {
        return disposableCups;
    }

    public void setDisposableCups(int disposableCups) {
        this.disposableCups = disposableCups;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public CoffeeMachineState getState() {
        return state;
    }

    public void setState(CoffeeMachineState state) {
        this.state = state;
    }

    private void actionBuy(String option) {
        CoffeeType type = null;
        if (option.equalsIgnoreCase("1")) {
            type = CoffeeType.ESPRESSO;
        } else if (option.equalsIgnoreCase("2")) {
            type = CoffeeType.LATTE;
        } else if (option.equalsIgnoreCase("3")) {
            type = CoffeeType.CAPPUCCINO;
        }

        if (type != null) {
            if (type.getWater() > getWaterAmount()) {
                System.out.println("Sorry, not enough water!");
            } else if (type.getMilk() > getMilkAmount()) {
                System.out.println("Sorry, not enough milk!");
            } else if (type.getCoffeeBeans() > getBeansAmount()) {
                System.out.println("Sorry, not enough coffee beans!");
            } else if (getDisposableCups() == 0) {
                System.out.println("Sorry, not enough disposable cups!");
            } else {
                System.out.println("I have enough resources, making you a coffee!");
                int water = getWaterAmount() - type.getWater();
                setWaterAmount(water);
                int milk = getMilkAmount() - type.getMilk();
                setMilkAmount(milk);
                int beans = getBeansAmount() - type.getCoffeeBeans();
                setBeansAmount(beans);
                int amount = getMoney() + type.getCost();
                setMoney(amount);
                int cups = getDisposableCups() - 1;
                setDisposableCups(cups);
            }
        }
    }

    private void fillResources(String resourceName, int amount) {
        switch (resourceName) {
            case "water":
                int water = getWaterAmount() + amount;
                setWaterAmount(water);
                break;

            case "milk":
                int milk = getMilkAmount() + amount;
                setMilkAmount(milk);
                break;

            case "beans":
                int beans = getBeansAmount() + amount;
                setBeansAmount(beans);
                break;

            case "cups":
                int cups = getDisposableCups() + amount;
                setDisposableCups(cups);
                break;

            default:
                break;
        }
    }

    private void remainingResources() {
        System.out.println("The coffee machine has:");
        System.out.println(getWaterAmount() + " of water");
        System.out.println(getMilkAmount() + " of milk");
        System.out.println(getBeansAmount() + " of coffee beans");
        System.out.println(getDisposableCups() + " of disposable cups");
        System.out.println("$" + getMoney() + " of money");
        System.out.println();
    }

    private void actionTake() {
        System.out.println("I gave you $" + getMoney() + "\n");
        setMoney(0);
    }

    public void prompt() {
        switch (this.state) {
            case IDLE:
                System.out.println("Write action (buy, fill, take, remaining, exit):");
                break;

            case BUY:
                System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                break;

            case FILL_WATER:
                System.out.println("Write how many ml of water do you want to add:");
                break;

            case FILL_MILK:
                System.out.println("Write how many ml of milk do you want to add:");
                break;

            case FILL_CUPS:
                System.out.println("Write how many disposable cups of coffee do you want to add:");
                break;

            case FILL_COFFEE_BEANS:
                System.out.println("Write how many grams of coffee beans do you want to add:");
                break;

            default:
                this.state = CoffeeMachineState.IDLE;
                break;
        }
    }

    public void doAction(String action) {

        if (this.state == CoffeeMachineState.IDLE) {
            action = action.toUpperCase();
            CoffeeMachineState actionState;
            try {
                 actionState = CoffeeMachineState.valueOf(action);
            } catch (IllegalArgumentException e) {
                actionState = CoffeeMachineState.IDLE;
            }


            switch (actionState) {
                case BUY:
                    setState(CoffeeMachineState.BUY);
                    break;

                case FILL:
                    setState(CoffeeMachineState.FILL_WATER);
                    break;

                case REMAINING:
                    remainingResources();
                    setState(CoffeeMachineState.IDLE);
                    break;

                case TAKE:
                    actionTake();
                    setState(CoffeeMachineState.IDLE);
                    break;

                case EXIT:
                    setState(CoffeeMachineState.EXIT);
                    break;

                default:
                    break;

            }
        } else if (getState() == CoffeeMachineState.BUY) {
            actionBuy(action);
            setState(CoffeeMachineState.IDLE);
        } else if (getState() == CoffeeMachineState.FILL_WATER) {
            fillResources("water", Integer.parseInt(action));
            setState(CoffeeMachineState.FILL_MILK);
        } else if (getState() == CoffeeMachineState.FILL_MILK) {
            fillResources("milk", Integer.parseInt(action));
            setState(CoffeeMachineState.FILL_COFFEE_BEANS);
        } else if (getState() == CoffeeMachineState.FILL_COFFEE_BEANS) {
            fillResources("beans", Integer.parseInt(action));
            setState(CoffeeMachineState.FILL_CUPS);
        } else if (getState() == CoffeeMachineState.FILL_CUPS) {
            fillResources("cups", Integer.parseInt(action));
            setState(CoffeeMachineState.IDLE);
        }

    }
}

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CoffeeMaker coffeeMaker = new CoffeeMaker();

        do {
            coffeeMaker.prompt();
            String action = scanner.nextLine();
            coffeeMaker.doAction(action);
        } while (coffeeMaker.getState() != CoffeeMachineState.EXIT);
    }
}
