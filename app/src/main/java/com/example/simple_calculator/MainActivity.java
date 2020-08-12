package com.example.simple_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button button0, button1, button2, button3, button4, button5, button6,
           button7, button8, button9, buttonAdd, buttonSub, buttonDiv, buttonEqual,
           buttonMul, buttonPoint, buttonPercent, buttonParenthesis, buttonC;

    TextView edttxt;

    boolean flagParenthesis = false;
    boolean calculateNewOperation = false;
    String screenTxt = "";
    String tmp = "";

    //static method deleteElem from lst also fixing the indexing when the element is removed
    public static String[] deleteElem(String[] lst, int index) {
        if (lst == null) { //just to make sure we don't break anything
            return lst;
        }
        String[] newlst = new String[lst.length - 1]; //we initialize a new list with less space as we remove one elem
        for (int i = 0, k = 0; i < lst.length; i++) {
            if (i == index) { //we skip the index that we want to remove
                continue;
            }
            newlst[k++] = lst[i]; //we have independent variables to
        }
        return newlst;
    }

    //Static method addElem to lst
    public static String[] addElem(String[] lst, String elem) {
        String[] newlst = new String[lst.length + 1]; //we initialize new lst with one extra space for new elem
        for (int i = 0; i < lst.length; i++){
            newlst[i] = lst[i]; //we add everything basically
        }
        newlst[lst.length] = elem; //but the last elem will be the new one
        return newlst;
    }


    //I HAVE AN IDEA FOR DEBUGGING THE PROBLEM
    //INSEAD OF * THEN / THEN + THEN -
    //YOU SHOULD DO PRIORITY TO * AND / THEN + AND -
    //WHOEVER COMES FIRST FROM THE PRIORITY 1 (* /) GETS CALCULATED THEN FROM PRIORITY 2 (+ -) GETS CALCULATED

    //method calculateWithNoParenthesis allows the full calculation of an operation that does not contain parenthesis (we can calculate what's inside parenthesis bro very useful)
    public static double calculateWithNoParenthesis(String[] listToCalculate){
        String[] tmp = new String[listToCalculate.length]; //create a copy of listToCalculate to be able to manipulate it
        for(int i = 0; i < tmp.length; i++){ //I think placing the listToCalculate in a seperate copy would help aleviate memory I'm not sure though
            tmp[i] = listToCalculate[i];
        }

        boolean flag = true; //to stop while loop
        int index = -1; //if no operator given is found we stay at -1

        //first we start with priority one (* and /)
        while(flag){
            index = -1; //we check again and again
            for(int i = tmp.length - 1; i >= 0; i--){ //in here we are looking for multiplication and division the first one specifically
                if(tmp[i].equals("*") || tmp[i].equals("/")) index = i; //priority 1 for calculation is * and /
            }
            //As we now know the indexes of multiplication or division we start calculating
            if(index == -1){
                //Do nothing because we don't have multiplication or division anymore or to begin with
                flag = false;
            }
            else{
                if(tmp[index].equals("*")){ //whatever comes first from priority 1 gets calculated first so it's basically calculating left to right
                    tmp[index] = String.valueOf(Double.valueOf(tmp[index-1])*Double.valueOf(tmp[index+1]));
                }
                else{//if it's division or .equals("/")
                    tmp[index] = String.valueOf(Double.valueOf(tmp[index-1])/Double.valueOf(tmp[index+1]));
                }
                tmp = deleteElem(tmp, index-1); //we remove the value behind the calculated value
                tmp = deleteElem(tmp, index); //and the one after which now is index + 1 (-1 [after we removed the one before])
            }
        }
        //reset
        flag = true;
        index = -1;

        //now we start with priority two (+ and -)
        while(flag){
            index = -1; //we check again and again
            for(int i = tmp.length - 1; i >= 0; i--){ //in here we are looking for addition and substraction the first one specifically
                if(tmp[i].equals("+") || tmp[i].equals("-")) index = i; //priority 2 for calculation is + and -
            }
            //As we now know the indexes of addition or substraction we start calculating
            if(index == -1){
                //Do nothing because we don't have addition or substraction anymore or to begin with
                flag = false;
            }
            else{
                if(tmp[index].equals("+")){ //whatever comes first from priority 2 gets calculated first so it's basically calculating left to right
                    tmp[index] = String.valueOf(Double.valueOf(tmp[index-1])+Double.valueOf(tmp[index+1]));
                }
                else{//if it's substraction or .equals("-")
                    tmp[index] = String.valueOf(Double.valueOf(tmp[index-1])-Double.valueOf(tmp[index+1]));
                }
                tmp = deleteElem(tmp, index-1); //we remove the value behind the calculated value
                tmp = deleteElem(tmp, index); //and the one after which now is index + 1 (-1 [after we removed the one before])
            }
        }
        //reset
        flag = true;
        index = -1;



        //Experiment 1 featuring * then / then + then - did not work as it created unwanted "parenthesis" in the calculations
        //THIS CODE DID NOT EFFICIENTLY CALCULATE LEFT TO RIGHT AS * FOR EXAMPLE IS CALCULATED FIRST EVEN IF IT's PLACED AFTER THE /
		/*
		//first we start with multiplication
		while(flag){
			index = -1; //we check again and again
			for(int i = 0; i < tmp.length; i++){ //in here we are looking for multiplication specifically the last one which would be easier for the code
				if(tmp[i].equals("*")) index = i;
			}
			//As we now know the indexes of multiplication we start calculating
			if(index == -1){
				//Do nothing because we don't have multiplication anymore or to begin with
				flag = false;
			}
			else{
				tmp[index] = String.valueOf(Double.valueOf(tmp[index-1])*Double.valueOf(tmp[index+1]));
				tmp = deleteElem(tmp, index-1); //we remove the value behind the mudtiplied value
				tmp = deleteElem(tmp, index); //and the one after which now is index + 1 (-1 [after we removed the one before])
			}
		}
		flag = true;
		index = -1;

		//now we start with division
		while(flag){
			index = -1; //we check again and again
			for(int i = 0; i < tmp.length; i++){ //in here we are looking for division specifically the last one which would be easier for the code
				if(tmp[i].equals("/")) index = i;
			}
			//As we now know the indexes of division we start calculating
			if(index == -1){
				//Do nothing because we don't have division anymore or to begin with
				flag = false;
			}
			else{
				tmp[index] = String.valueOf(Double.valueOf(tmp[index-1])/Double.valueOf(tmp[index+1]));
				tmp = deleteElem(tmp, index-1); //we remove the value behind the mudtiplied value
				tmp = deleteElem(tmp, index); //and the one after which now is index + 1 (-1 [after we removed the one before])
			}
		}
		flag = true;
		index = -1;

		//now we start with addition
		while(flag){
			index = -1; //we check again and again
			for(int i = 0; i < tmp.length; i++){ //in here we are looking for addition specifically the last one which would be easier for the code
				if(tmp[i].equals("+")) index = i;
			}
			//As we now know the indexes of addition we start calculating
			if(index == -1){
				//Do nothing because we don't have addition anymore or to begin with
				flag = false;
			}
			else{
				tmp[index] = String.valueOf(Double.valueOf(tmp[index-1])+Double.valueOf(tmp[index+1]));
				tmp = deleteElem(tmp, index-1); //we remove the value behind the mudtiplied value
				tmp = deleteElem(tmp, index); //and the one after which now is index + 1 (-1 [after we removed the one before])
			}
		}
		flag = true;
		index = -1;

		//now we start with substraction
		while(flag){
			index = -1; //we check again and again
			for(int i = 0; i < tmp.length; i++){ //in here we are looking for substraction specifically the last one which would be easier for the code
				if(tmp[i].equals("-")) index = i;
			}
			//As we now know the indexes of substraction we start calculating
			if(index == -1){
				//Do nothing because we don't have substraction anymore or to begin with
				flag = false;
			}
			else{
				tmp[index] = String.valueOf(Double.valueOf(tmp[index-1])-Double.valueOf(tmp[index+1]));
				tmp = deleteElem(tmp, index-1); //we remove the value behind the mudtiplied value
				tmp = deleteElem(tmp, index); //and the one after which now is index + 1 (-1 [after we removed the one before])
			}
		}
		flag = true;
		index = -1;
		*/

        return Double.valueOf(tmp[0]); //return variable only allows Doubles not Strings
    }

    //method calculate uses above methods with addition to inside code and recursion to calculate an entire operation and can calculate with multiple parenthesis'
    public static double calculate(String toCalculate){
        //first we place spaces between operators and numbers and change numbers to double
        String spacingString = toCalculate;
        String tmp = "";
        boolean isThereParenthesis = false;

        //we are creating spaces between operators and numbers so that we are able to place them all in a list efficiently with function .split(" ")
        for(int i = 0; i < spacingString.length(); i++){
            if(spacingString.charAt(i) == '/'){
                tmp += " / ";
            }
            else if(spacingString.charAt(i) == '*'){
                tmp += " * ";
            }
            else if(spacingString.charAt(i) == '+'){
                tmp += " + ";
            }
            else if(spacingString.charAt(i) == '-'){ //especially when a recursive method is called there will sometimes be negative numbers which the code should recognize as not operators
                try{ //if the - is in the middle of the operation
                    if(spacingString.charAt(i-1) == '*' ||
                            spacingString.charAt(i-1) == '/' ||
                            spacingString.charAt(i-1) == '+' ||
                            spacingString.charAt(i-1) == '-' ||
                            spacingString.charAt(i-1) == '('){ //when an operator is found behind - we assume it is negating a number
                        tmp += " -";
                    }
                    else{ //or it might as well just be an operator itself
                        tmp += " - ";
                    }
                }
                catch(Exception e){ //If the - is in the beginning we assume it negates a number
                    tmp += "-";
                }
            }
            else if(spacingString.charAt(i) == ')'){
                tmp += " )"; //we have to assume the code in the calculator placed an operator or nothing after the )
                isThereParenthesis = true;
            }
            else if(spacingString.charAt(i) == '('){
                tmp += "( "; //we have to assume the code in the calculator placed an operator or nothing before the (
                isThereParenthesis = true;
            }
            else{
                tmp += String.valueOf(spacingString.charAt(i));
            }
        }
        toCalculate = tmp;

        //Experiment one using replaceALL() failed
		/*
		spacingString = toCalculate.replaceAll("/"," / ");
		spacingString = toCalculate.replaceAll("*"," * ");
		spacingString = toCalculate.replaceAll("+"," + ");
		spacingString = toCalculate.replaceAll("-"," - ");
		spacingString = toCalculate.replaceAll("("," ( ");
		spacingString = toCalculate.replaceAll(")"," ) ");
		*/

        String[] listToCalculate = toCalculate.split(" "); //Change to list

		/*
		for(int i = 0; i < listToCalculate.length; i++){
			try{
				System.out.println(Double.valueOf(listToCalculate[i]));
			}
			catch(Exception e){
				System.out.println(listToCalculate[i]);
			}
		}
		*/

        if(isThereParenthesis){ //if we have a parenthesis we will calculate what's inside first then if there's another parenthesis we do a recursion preferably to this line

            //THIS CODE ONLY WORKS WITH ONE PARENTHESIS AND ONE ONLY
            /*
            String[] tmptmp = {};
            boolean parenthesisFlag = false;
            int indexToPlaceAnswer = 0;
            for(int i = 0; i < listToCalculate.length; i++){ //FOR LOOP MAKES CODE RUN TO OTHER PARENTHESISES IN THE SAME OPERATION
                if(listToCalculate[i].equals(")")){
                    parenthesisFlag = false; //when we finish the parenthesis we stop adding to the list
                }

                if(parenthesisFlag){ //we add parenthesis elements to a seperate list to calculate seperately
                    tmptmp = addElem(tmptmp, listToCalculate[i]);
                }


                if(listToCalculate[i].equals("(")){
                    parenthesisFlag = true; //when we find a parenthesis we start placing what's in there in another list
                    indexToPlaceAnswer = i;
                }
            }

            double inTheParenthesis = calculateWithNoParenthesis(tmptmp);
            listToCalculate[indexToPlaceAnswer] = String.valueOf(inTheParenthesis); //place the final answer from the parenthesis instead of (

            indexToPlaceAnswer++; //we'll use the same variables here to save memory as an experimental try
            while(!parenthesisFlag){
                listToCalculate = deleteElem(listToCalculate, indexToPlaceAnswer); //we are removing what's in the parenthesis
                if(listToCalculate[indexToPlaceAnswer].equals(")")){
                    parenthesisFlag = true;
                    listToCalculate = deleteElem(listToCalculate, indexToPlaceAnswer); //we remove one last time the )
                }
            }

            return calculateWithNoParenthesis(listToCalculate);
			*/

            String[] tmptmp = {}; //this will house the operation inside the first parenthesis in the master operation which will then be calculated
            boolean parenthesisFlag = true; //to stop while looping forever (without this variable I would probably fail this assignment)
            boolean additionFlag = false; //we only want to add elements to our tmptmp when we arrive to the parenthesis
            int indexToPlaceAnswer = 0; //not -1 because we will put it somewhere at some point (easier for memory)

            int tmpindex = 0;
            while(parenthesisFlag){ //we are trying while loops just to use less time of running through the entire list
                if(listToCalculate[tmpindex].equals(")")){ //we stop adding and we certainly don't add the )
                    parenthesisFlag = false;
                }

                if(parenthesisFlag && additionFlag){ //important because we don't want to add the ) to our tmp
                    tmptmp = addElem(tmptmp, listToCalculate[tmpindex]); //using addElem method to add the elems
                }

                if(listToCalculate[tmpindex].equals("(")){
                    indexToPlaceAnswer = tmpindex; //we want to place the final answer instead of ( then remove the rest until )
                    additionFlag = true;
                }

                tmpindex++; //next index
            }

            listToCalculate[indexToPlaceAnswer] = String.valueOf(calculateWithNoParenthesis(tmptmp)); //we place the parenthesis answer instead of (


            indexToPlaceAnswer++;//we will start removing elements after the answer
            while(!parenthesisFlag){
                listToCalculate = deleteElem(listToCalculate, indexToPlaceAnswer); //we are removing what's in the parenthesis
                if(listToCalculate[indexToPlaceAnswer].equals(")")){
                    parenthesisFlag = true;
                    listToCalculate = deleteElem(listToCalculate, indexToPlaceAnswer); //we remove one last time the )
                }
            }

            parenthesisFlag = false; //we'll be using this to check if there's other parenthesis' in the operation
            String sOfListToCalculate = ""; //recursion only allows us to input a String and not a list so it's best to put everything in a String and restart and here we check if we have more parenthesis'
            for(int i = 0; i < listToCalculate.length; i++){ //worst case while loop and for loop is the same O(n) so I should probably use for loops they're easier to write
                sOfListToCalculate += listToCalculate[i];
                if(listToCalculate[i].equals("(")){
                    parenthesisFlag = true;
                }
            }

            if(parenthesisFlag){ //recursive method to calculate all parenthesis' before calculating entire operation
                return calculate(sOfListToCalculate);
            }
            else{ //if no parenthesis' found just calculate
                return calculateWithNoParenthesis(listToCalculate);
            }

        }
        else{ //if there are no parenthesis' we just calculate the operation
            return calculateWithNoParenthesis(listToCalculate);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //connecing buttons to the code
        button0 = (Button) findViewById(R.id.btn0);
        button1 = (Button) findViewById(R.id.btn1);
        button2 = (Button) findViewById(R.id.btn2);
        button3 = (Button) findViewById(R.id.btn3);
        button4 = (Button) findViewById(R.id.btn4);
        button5 = (Button) findViewById(R.id.btn5);
        button6 = (Button) findViewById(R.id.btn6);
        button7 = (Button) findViewById(R.id.btn7);
        button8 = (Button) findViewById(R.id.btn8);
        button9 = (Button) findViewById(R.id.btn9);
        buttonAdd = (Button) findViewById(R.id.btnplus);
        buttonSub = (Button) findViewById(R.id.btnminus);
        buttonDiv = (Button) findViewById(R.id.btndivide);
        buttonEqual = (Button) findViewById(R.id.btnequal);
        buttonMul = (Button) findViewById(R.id.btntimes);
        buttonPoint = (Button) findViewById(R.id.btndot);
        buttonPercent = (Button) findViewById(R.id.btnpercent);
        buttonParenthesis = (Button) findViewById(R.id.btnparenthesis);
        buttonC = (Button) findViewById(R.id.btnclear);

        //connecting the screen to the code
        edttxt = (TextView) findViewById(R.id.screen);

        //when button 0 is pressed we run this code
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(calculateNewOperation){ //after we pressed equals and we want to make a new calculation we don't need to press C just any button
                    screenTxt = "";
                    edttxt.setText("");
                    flagParenthesis = false;
                    calculateNewOperation = false; //false because any other button pressed will trigger a loop of reset
                }

                screenTxt += "0";
                edttxt.setText(edttxt.getText() + "0");
            }
        });

        //when button 1 is pressed we run this code
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(calculateNewOperation){ //after we pressed equals and we want to make a new calculation we don't need to press C just any button
                    screenTxt = "";
                    edttxt.setText("");
                    flagParenthesis = false;
                    calculateNewOperation = false; //false because any other button pressed will trigger a loop of reset
                }

                screenTxt += "1";
                edttxt.setText(edttxt.getText() + "1");
            }
        });

        //when button 2 is pressed we run this code
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(calculateNewOperation){ //after we pressed equals and we want to make a new calculation we don't need to press C just any button
                    screenTxt = "";
                    edttxt.setText("");
                    flagParenthesis = false;
                    calculateNewOperation = false; //false because any other button pressed will trigger a loop of reset
                }

                screenTxt += "2";
                edttxt.setText(edttxt.getText() + "2");
            }
        });

        //when button 3 is pressed we run this code
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(calculateNewOperation){ //after we pressed equals and we want to make a new calculation we don't need to press C just any button
                    screenTxt = "";
                    edttxt.setText("");
                    flagParenthesis = false;
                    calculateNewOperation = false; //false because any other button pressed will trigger a loop of reset
                }

                screenTxt += "3";
                edttxt.setText(edttxt.getText() + "3");
            }
        });

        //when button 4 is pressed we run this code
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(calculateNewOperation){ //after we pressed equals and we want to make a new calculation we don't need to press C just any button
                    screenTxt = "";
                    edttxt.setText("");
                    flagParenthesis = false;
                    calculateNewOperation = false; //false because any other button pressed will trigger a loop of reset
                }

                screenTxt += "4";
                edttxt.setText(edttxt.getText() + "4");
            }
        });

        //when button 5 is pressed we run this code
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(calculateNewOperation){ //after we pressed equals and we want to make a new calculation we don't need to press C just any button
                    screenTxt = "";
                    edttxt.setText("");
                    flagParenthesis = false;
                    calculateNewOperation = false; //false because any other button pressed will trigger a loop of reset
                }

                screenTxt += "5";
                edttxt.setText(edttxt.getText() + "5");
            }
        });

        //when button 6 is pressed we run this code
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(calculateNewOperation){ //after we pressed equals and we want to make a new calculation we don't need to press C just any button
                    screenTxt = "";
                    edttxt.setText("");
                    flagParenthesis = false;
                    calculateNewOperation = false; //false because any other button pressed will trigger a loop of reset
                }

                screenTxt += "6";
                edttxt.setText(edttxt.getText() + "6");
            }
        });

        //when button 7 is pressed we run this code
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(calculateNewOperation){ //after we pressed equals and we want to make a new calculation we don't need to press C just any button
                    screenTxt = "";
                    edttxt.setText("");
                    flagParenthesis = false;
                    calculateNewOperation = false; //false because any other button pressed will trigger a loop of reset
                }

                screenTxt += "7";
                edttxt.setText(edttxt.getText() + "7");
            }
        });

        //when button 8 is pressed we run this code
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(calculateNewOperation){ //after we pressed equals and we want to make a new calculation we don't need to press C just any button
                    screenTxt = "";
                    edttxt.setText("");
                    flagParenthesis = false;
                    calculateNewOperation = false; //false because any other button pressed will trigger a loop of reset
                }

                screenTxt += "8";
                edttxt.setText(edttxt.getText() + "8");
            }
        });

        //when button 9 is pressed we run this code
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(calculateNewOperation){ //after we pressed equals and we want to make a new calculation we don't need to press C just any button
                    screenTxt = "";
                    edttxt.setText("");
                    flagParenthesis = false;
                    calculateNewOperation = false; //false because any other button pressed will trigger a loop of reset
                }

                screenTxt += "9";
                edttxt.setText(edttxt.getText() + "9");
            }
        });

        //when button + is pressed we run this code
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(calculateNewOperation){ //after we pressed equals and we want to make a new calculation we don't need to press C just any button
                    screenTxt = "";
                    edttxt.setText("");
                    flagParenthesis = false;
                    calculateNewOperation = false; //false because any other button pressed will trigger a loop of reset
                }

                try{ //If there are characters behind the operator we will check specifically for operators
                    //We check if there is a starting parenthesis in which case we don't place the operator
                    if(screenTxt.charAt(screenTxt.length() - 1) == '('){
                        //Do nothing (Don't add the operator
                    }

                    else if((screenTxt.charAt(screenTxt.length() - 1) == '*') ||
                   (screenTxt.charAt(screenTxt.length() - 1) == '/') ||
                   (screenTxt.charAt(screenTxt.length() - 1) == '-') ||
                   (screenTxt.charAt(screenTxt.length() - 1) == '+')){ //if there are operators
                        screenTxt = screenTxt.substring(0, screenTxt.length()-1); //we remove the last operator from the String
                        tmp = edttxt.getText().toString();
                        tmp = tmp.substring(0, tmp.length()-1);
                        tmp = tmp.substring(0, tmp.length()-1);
                        tmp = tmp.substring(0, tmp.length()-1); //we also remove the operator from the screen
                        edttxt.setText(tmp);

                        //then we add the new operator
                        screenTxt += "+";
                        edttxt.setText(edttxt.getText() + " + ");
                    }

                    else{ //if no operator is behind the one asked for we just add it
                        screenTxt += "+";
                        edttxt.setText(edttxt.getText() + " + ");
                    }
                }
                catch (Exception e){ //If there are no characters we won't place an operator

                }
            }
        });

        //when button - is pressed we run this code
        buttonSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(calculateNewOperation){ //after we pressed equals and we want to make a new calculation we don't need to press C just any button
                    screenTxt = "";
                    edttxt.setText("");
                    flagParenthesis = false;
                    calculateNewOperation = false; //false because any other button pressed will trigger a loop of reset
                }

                try{ //If there are characters behind the operator we will check specifically for operators
                    //We check if there is a starting parenthesis in which case we don't place the operator
                    if(screenTxt.charAt(screenTxt.length() - 1) == '('){
                        //Do nothing (Don't add the operator
                    }

                    else if((screenTxt.charAt(screenTxt.length() - 1) == '*') ||
                            (screenTxt.charAt(screenTxt.length() - 1) == '/') ||
                            (screenTxt.charAt(screenTxt.length() - 1) == '-') ||
                            (screenTxt.charAt(screenTxt.length() - 1) == '+')){ //if there are operators
                        screenTxt = screenTxt.substring(0, screenTxt.length()-1); //we remove the last operator from the String
                        tmp = edttxt.getText().toString();
                        tmp = tmp.substring(0, tmp.length()-1);
                        tmp = tmp.substring(0, tmp.length()-1);
                        tmp = tmp.substring(0, tmp.length()-1); //we also remove the operator from the screen
                        edttxt.setText(tmp);

                        //then we add the new operator
                        screenTxt += "-";
                        edttxt.setText(edttxt.getText() + " - ");
                    }

                    else{ //if no operator is behind the one asked for we just add it
                        screenTxt += "-";
                        edttxt.setText(edttxt.getText() + " - ");
                    }
                }
                catch (Exception e){ //If there are no characters we won't place an operator

                }
            }
        });

        //when button / is pressed we run this code
        buttonDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(calculateNewOperation){ //after we pressed equals and we want to make a new calculation we don't need to press C just any button
                    screenTxt = "";
                    edttxt.setText("");
                    flagParenthesis = false;
                    calculateNewOperation = false; //false because any other button pressed will trigger a loop of reset
                }

                try{ //If there are characters behind the operator we will check specifically for operators
                    //We check if there is a starting parenthesis in which case we don't place the operator
                    if(screenTxt.charAt(screenTxt.length() - 1) == '('){
                        //Do nothing (Don't add the operator
                    }

                    else if((screenTxt.charAt(screenTxt.length() - 1) == '*') ||
                            (screenTxt.charAt(screenTxt.length() - 1) == '/') ||
                            (screenTxt.charAt(screenTxt.length() - 1) == '-') ||
                            (screenTxt.charAt(screenTxt.length() - 1) == '+')){ //if there are operators
                        screenTxt = screenTxt.substring(0, screenTxt.length()-1); //we remove the last operator from the String
                        tmp = edttxt.getText().toString();
                        tmp = tmp.substring(0, tmp.length()-1);
                        tmp = tmp.substring(0, tmp.length()-1);
                        tmp = tmp.substring(0, tmp.length()-1); //we also remove the operator from the screen
                        edttxt.setText(tmp);

                        //then we add the new operator
                        screenTxt += "/";
                        edttxt.setText(edttxt.getText() + " / ");
                    }

                    else{ //if no operator is behind the one asked for we just add it
                        screenTxt += "/";
                        edttxt.setText(edttxt.getText() + " / ");
                    }
                }
                catch (Exception e){ //If there are no characters we won't place an operator

                }
            }
        });

        //when button = is pressed we run this code
        buttonEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                try {//only do equal method if something is inputted or if something uncalculatable is inputted we don't output anything
                    if ((screenTxt.charAt(screenTxt.length() - 1) == '*') ||
                            (screenTxt.charAt(screenTxt.length() - 1) == '/') ||
                            (screenTxt.charAt(screenTxt.length() - 1) == '-') ||
                            (screenTxt.charAt(screenTxt.length() - 1) == '+')) {
                        screenTxt = screenTxt.substring(0, screenTxt.length() - 1);
                        tmp = edttxt.getText().toString();
                        tmp = tmp.substring(0, tmp.length() - 1);
                        tmp = tmp.substring(0, tmp.length() - 1);
                        tmp = tmp.substring(0, tmp.length() - 1);
                        edttxt.setText(tmp);
                    }

                    if(flagParenthesis){ //If we have placed a parenthesis already
                        //check if the parenthesis was not left empty and if so remove from string and the
                        if ((screenTxt.charAt(screenTxt.length() - 1) == '(')){
                            try{ // we will try and take out 4 characters
                                screenTxt = screenTxt.substring(0, screenTxt.length()-1); //we remove the parenthesis from the String
                                screenTxt = screenTxt.substring(0, screenTxt.length()-1); //we remove the operator too
                                tmp = edttxt.getText().toString();
                                tmp = tmp.substring(0, tmp.length()-1);
                                tmp = tmp.substring(0, tmp.length()-1);
                                tmp = tmp.substring(0, tmp.length()-1); //we also remove the operator from the screen
                                tmp = tmp.substring(0, tmp.length()-1);
                                edttxt.setText(tmp);
                            }
                            catch (Exception e){ //if we can't we assume we are starting with a parenthesis meaning we start from the beginning
                                //taken from the buttonC method
                                screenTxt = "";
                                edttxt.setText("");
                                flagParenthesis = false;
                            }
                        }

                        else{//If the parenthesis was not left empty
                            screenTxt += ")";
                            edttxt.setText(edttxt.getText() + ")");
                            flagParenthesis = false;
                        }
                    }
                    calculateNewOperation = true;
                    edttxt.setText(String.valueOf(calculate(screenTxt)));
                }
                catch(Exception e){
                    //in the event that nothing has been inputted we will not do anything
                }
            }
        });

        //when button x is pressed we run this code
        buttonMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(calculateNewOperation){ //after we pressed equals and we want to make a new calculation we don't need to press C just any button
                    screenTxt = "";
                    edttxt.setText("");
                    flagParenthesis = false;
                    calculateNewOperation = false; //false because any other button pressed will trigger a loop of reset
                }

                try{ //If there are characters behind the operator we will check specifically for operators
                    //We check if there is a starting parenthesis in which case we don't place the operator
                    if(screenTxt.charAt(screenTxt.length() - 1) == '('){
                        //Do nothing (Don't add the operator
                    }
                    else if((screenTxt.charAt(screenTxt.length() - 1) == '*') ||
                            (screenTxt.charAt(screenTxt.length() - 1) == '/') ||
                            (screenTxt.charAt(screenTxt.length() - 1) == '-') ||
                            (screenTxt.charAt(screenTxt.length() - 1) == '+')){ //if there are operators
                        screenTxt = screenTxt.substring(0, screenTxt.length()-1); //we remove the last operator from the String
                        tmp = edttxt.getText().toString();
                        tmp = tmp.substring(0, tmp.length()-1);
                        tmp = tmp.substring(0, tmp.length()-1);
                        tmp = tmp.substring(0, tmp.length()-1); //we also remove the operator from the screen
                        edttxt.setText(tmp);

                        //then we add the new operator
                        screenTxt += "*";
                        edttxt.setText(edttxt.getText() + " x ");
                    }

                    else{ //if no operator is behind the one asked for we just add it
                        screenTxt += "*";
                        edttxt.setText(edttxt.getText() + " x ");
                    }
                }
                catch (Exception e){ //If there are no characters we won't place an operator

                }
            }
        });

        //when button . is pressed we run this code
        buttonPoint.setOnClickListener(new View.OnClickListener() {//MAKE SURE ONLY ONE TIME IS PRESSED IN A NUMBER
            @Override
            public void onClick(View v){
                if(calculateNewOperation){ //after we pressed equals and we want to make a new calculation we don't need to press C just any button
                    screenTxt = "";
                    edttxt.setText("");
                    flagParenthesis = false;
                    calculateNewOperation = false; //false because any other button pressed will trigger a loop of reset
                }

                try{//only place . if a number is behind
                    if(screenTxt.charAt(screenTxt.length() - 1) >= '0' && screenTxt.charAt(screenTxt.length() - 1) <= '9'){
                        screenTxt += ".";
                        edttxt.setText(edttxt.getText() + ".");
                    }
                }
                catch (Exception e){ //If no characters are behind the . we don't write

                }
            }
        });

        //when button % is pressed we run this code
        buttonPercent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(calculateNewOperation){ //after we pressed equals and we want to make a new calculation we don't need to press C just any button
                    screenTxt = "";
                    edttxt.setText("");
                    flagParenthesis = false;
                    calculateNewOperation = false; //false because any other button pressed will trigger a loop of reset
                }

                try{//I'm using all these trys because if no character has been placed at the beginning and this runs .charAt(-1) will crash the app
                    if(screenTxt.charAt(screenTxt.length() - 1) >= '0' && screenTxt.charAt(screenTxt.length() - 1) <= '9'){
                        screenTxt += "/100";
                        edttxt.setText(edttxt.getText() + "%");
                    }
                }
                catch (Exception e){ //If no characters are behind the % we won't bother doing anything it's just I'm forced to write the catch method it's a waste of time typing my guy

                }

            }
        });

        //when button () is pressed we run this code
        buttonParenthesis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(calculateNewOperation){ //after we pressed equals and we want to make a new calculation we don't need to press C just any button
                    screenTxt = "";
                    edttxt.setText("");
                    flagParenthesis = false;
                    calculateNewOperation = false; //false because any other button pressed will trigger a loop of reset
                }

                if(flagParenthesis){ //If we have placed a parenthesis already
                    //check if the parenthesis was not left empty and if so remove from string and the
                    if ((screenTxt.charAt(screenTxt.length() - 1) == '(')){
                        try{ // we will try and take out 4 characters
                            screenTxt = screenTxt.substring(0, screenTxt.length()-1); //we remove the parenthesis from the String
                            screenTxt = screenTxt.substring(0, screenTxt.length()-1); //we remove the operator too
                            tmp = edttxt.getText().toString();
                            tmp = tmp.substring(0, tmp.length()-1);
                            tmp = tmp.substring(0, tmp.length()-1);
                            tmp = tmp.substring(0, tmp.length()-1); //we also remove the operator from the screen
                            tmp = tmp.substring(0, tmp.length()-1);
                            edttxt.setText(tmp);
                        }
                        catch (Exception e){ //if we can't we assume we are starting with a parenthesis meaning we start from the beginning
                            //taken from the buttonC method
                            screenTxt = "";
                            edttxt.setText("");
                            flagParenthesis = false;
                        }
                    }

                    else{//If the parenthesis was not left empty
                        screenTxt += ")";
                        edttxt.setText(edttxt.getText() + ")");
                        flagParenthesis = false;
                    }
                }
                else{ //If we have not yet placed a parenthesis
                    try {//If we have placed something in the screen
                        if ((screenTxt.charAt(screenTxt.length() - 1) == '*') ||
                                (screenTxt.charAt(screenTxt.length() - 1) == '/') ||
                                (screenTxt.charAt(screenTxt.length() - 1) == '-') ||
                                (screenTxt.charAt(screenTxt.length() - 1) == '+')) { // If an operator has been placed we will use the operator instead of a multiplication
                            screenTxt += "(";
                            edttxt.setText(edttxt.getText() + "(");
                            flagParenthesis = true;
                        } else { //Assuming we want to multiply by the parenthesis
                            screenTxt += "*(";
                            edttxt.setText(edttxt.getText() + " x ("); //Let the user know there is a multiplication assumed
                            flagParenthesis = true;
                        }
                    }
                    catch (Exception e){//and if we haven't we assume we want to place one opening parenthesis in the beginning
                        screenTxt += "(";
                        edttxt.setText(edttxt.getText() + "(");
                        flagParenthesis = true;
                    }
                }
            }
        });

        //when button C is pressed we run this code
        buttonC.setOnClickListener(new View.OnClickListener() { //Restart everything
            @Override
            public void onClick(View v){
                screenTxt = "";
                edttxt.setText("");
                flagParenthesis = false;
            }
        });


    }
}