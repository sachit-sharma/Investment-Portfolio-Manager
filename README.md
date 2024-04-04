# Investment Portfolio Manager

## A System to Track my Stocks

1. **What will my application do?** 

My application will help a user track how much money they have invested in different stocks.On this app, the user would be able to log any new investment or withdrawals they make,along with 
details such as stock name, price, number of units purchased, company type etc. Some features of my app would include 
a visualisation of how much money has been invested in different sectors, calculation of the return on the stock based on the current price given by the user.
It would allow users to view their total profit on all their stocks. 

2. **Who will use it?** 

- People who invest in the stock market.

This app would be most useful for people who regularly make investments in the market and want to diversify their portfolio. This can help them keep track of how much money they have invested in different avenues. 


3. **Why am I doing this?**

This project is of interest to me because as someone who regularly invests money in the stock market, a system to track investments would be incredibly useful to me. 
It would ensure that I am meeting my goals of making regular and diverse investments.  It would also help me plan for the future by predicting how much I might be able to earn on these investments. 

## User Stories

- I want to be able to add a stock to my list of investments and specify its category, number of units bought, stock price. 
- I want to be able to sell a stock, adding its profit to my realised profits. 
- I want to be able to view all my stocks. 
- I want to be able to update the prices of any stock and be shown the return on that particular stock.  
- I want to be able to calculate how much money I have invested, by category. (for instance, health care sector, tech, pharmaceuticals etc.)
- I want to be able to have the option to load my stock portfolio from file and resume where I left off. 
- I want to have the option to save the state of my stock portfolio (all stocks with their characteristics). 


# Instructions for Grader

- You can generate the first required action related to the user story "adding multiple Xs to a Y" by clicking the
  "Add stock" menu item in the portfolio menu and following the instructions
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by clicking the "Sell
  Stock" menu item in the portfolio menu and following instructions that appear.
- You can locate my visual component in the home screen on the right panel (an image of the stock market)
- You can save the state of my application by clicking the save button.
- You can reload the state of my application by clicking the load button. 

### Phase 4 Task 2:

- A representative set of events that may happen are:

1. Adding a Stock named "X" to the portfolio.
2. Adding a Stock named "Y" to the portfolio.
3. Adding a Stock named "Z" to the portfolio.
3. Selling the stock named "Y" at a price 100

This looks like:

  - Tue Apr 02 11:45:36 PDT 2024 
   Added X to portfolio 
  - Tue Apr 02 11:45:42 PDT 2024
   Added Y to portfolio
  -  Tue Apr 02 11:45:51 PDT 2024
   Added Z to portfolio
   - Tue Apr 02 11:46:00 PDT 2024
   Sold Y at 100.0

### Pase 4 Task 3:

Looking at my UML diagram, I believe that I could have split Stock Portfolio into two classes- a profit manager and stocks list class. 
Currently, my stock portfolio has two responsibilities: to maintain a list of all stocks and calculate the total realised profit. This change can thus increase 
cohesion according to the single responsibility principle. Additonally, since I want the profit manager to only have a single instance, I would have considered using the singleton pattern
here. 

Another area where I might have worked on, if I had more time, is making my classes robust. I was not able to make the methods in the Stock Portfolio and Stock classes robust, 
so I would like to throw and catch exceptions instead of relying on user input. For example, 
in my design, I am relying on the user to provide the right category of Stocks (out of the 7 categories I have), but it would be better if I used exception handling.
Lastly, I would have made a new class (an enum) for the categories of my stocks as this would allow me to easily add or change categories in my app.
In my current design, changing a category would involve changes to many different methods and would be an extremely tedious task. Having a class dedicated to the stock categories, would allow me 
to add or remove categories easily. 


