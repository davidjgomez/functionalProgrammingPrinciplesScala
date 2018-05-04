package recfun

import scala.annotation.tailrec

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
    def pascal(c: Int, r: Int): Int = {
      if(c == 0 || c == r) 1
      else pascal(c-1, r-1) + pascal(c, r-1)
    }
  
  /**
   * Exercise 2
   */
    def balance(chars: List[Char]): Boolean = {
      
      def balance1(chars: List[Char], value: Int): Boolean = {
        if(!chars.isEmpty) {
          balance1(chars.tail, 
            if(chars.head.equals('(')) value + 1 
            else if(chars.head.equals(')')) {
              if(value > 0) value - 1
              else value + 1
            }
            else value)
        }
        else value == 0
      }
      
      balance1(chars, 0)
    }
    
  /**
   * Exercise 3
   */
    def countChange(money: Int, coins: List[Int]): Int = {
           
      def countChange1(coins: List[Int], sum: Int): Int = {
        if(money <= 0 || coins.isEmpty) 0
        else {
          def sum1 = sum + coins.head
           
          def value = if(sum1 == money) 1
          else if(sum1 < money) countChange1(coins, sum1)
          else 0
          
          value + countChange1(coins.tail, sum)
        }
      }
       
      countChange1(coins, 0)
    }
  }
