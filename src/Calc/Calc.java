/*
Принцип работы алгоритма Дейкстра:

Проходим исходную строку;
При нахождении числа, заносим его в выходную строку;
При нахождении оператора, заносим его в стек;
Выталкиваем в выходную строку из стека все операторы, имеющие приоритет выше рассматриваемого;
При нахождении открывающейся скобки, заносим её в стек;
При нахождении закрывающей скобки, выталкиваем из стека все операторы до открывающейся скобки, а открывающуюся скобку удаляем из стека. */

//https://ru.wikipedia.org/wiki/%D0%9E%D0%B1%D1%80%D0%B0%D1%82%D0%BD%D0%B0%D1%8F_%D0%BF%D0%BE%D0%BB%D1%8C%D1%81%D0%BA%D0%B0%D1%8F_%D0%B7%D0%B0%D0%BF%D0%B8%D1%81%D1%8C

package Calc;

public class Calc {
    private Integer[] elems = new Integer[2];
    private String op;
    private int res;
    public TypeRimeOrArabian type = TypeRimeOrArabian.UNDEFINED;

    public String calculate(String inputString) throws CustomException {
        int el1 = elems[0], el2= elems[1];

        switch (op) {
            case "+":
                res = el1 + el2;
                break;
            case "-":
                res = el1 - el2;
                break;
            case "*":
                res = el1 * el2;
                break;
            case "/":
                res = el1 / el2;
                break;
            default:
                throw new CustomException("Calc exception: unknown operation: " + op + " ");
        }

        if(type==TypeRimeOrArabian.RIME){
            if(res<=0){
                throw new CustomException("Calc Exception: result in RIME digit cant be ZERO or MINUS: " + Integer.toString(res)+"["+inputString+"]");
            }
        }

        String strRes="";
        if(type == TypeRimeOrArabian.ARABIAN) {
            strRes = Integer.toString(res);
        } else {
            strRes = RimeDigits.getRime(res);
        }
        return strRes;
    }


    public String separate(String str) throws CustomException {
        String el;
        String[] splitedStr;
        int countEl = 0, intElem=0;
        boolean isDigit=false, wrongType=false, wrongInterval=false,firstMinus=false;

        op = "";
        elems[0] = 0;
        elems[1] = 0;
        type = TypeRimeOrArabian.UNDEFINED;

        //разложим на массив из чисел и знаков операций по разделителю пробел
        str = str.replaceAll("\\+", " + ");
        str = str.replaceAll("-", " - ");
        str = str.replaceAll("\\*", " * ");
        str = str.replaceAll("/", " / ");
        str = str.replaceAll("=", "");
        splitedStr = str.split(" ");

        for (int elemCounter = 0; elemCounter < splitedStr.length; elemCounter++) {

            el = splitedStr[elemCounter];

            if (el.equals("")) {
                continue;
            } //возможны пустые строки от лишних пробелов - пропустим

            if((countEl==0)&&el.equals("-")){
                firstMinus=true;
                continue;
            }
            countEl++;

            if (countEl == 1) {
                if (isArabianDigit(el)) {
                    System.out.print("[Arabian] ");
                    type = TypeRimeOrArabian.ARABIAN;
                    intElem = Integer.parseInt(el);
                    elems[0] = intElem;

                    if(firstMinus){
                        elems[0]=-elems[0];
                    }
                    if ((intElem<1)||(intElem>10)){
                        wrongInterval=true;
                    }
                } else if (isRimeDigit(el)) {
                    System.out.print("[Rime] ");
                    type = TypeRimeOrArabian.RIME;
                    intElem = RimeDigits.getArabian(el.toUpperCase());
                    elems[0] = intElem;
                    if ((intElem<1)||(intElem>10)){
                        wrongInterval=true;
                    }
                } else {
                    throw new CustomException("Separate Exception: error digit in first position: " + el);
                }
            }

            if (countEl == 2) {
                if (isOp(el)) {
                    op = el;
                } else {
                    throw new CustomException("Separate exception: unknown operation: " + op + " ");
                }
            }


            if (countEl == 3) {
                if (isArabianDigit(el)) {
                    if (type == TypeRimeOrArabian.ARABIAN) {
                        intElem = Integer.parseInt(el);
                        elems[1] = intElem;
                        isDigit = true;
                        if ((intElem<1)||(intElem>10)){
                            wrongInterval=true;
                        }

                    } else {
                        wrongType = true;
                    }
                }

                if (isRimeDigit(el)) {
                    if (type == TypeRimeOrArabian.RIME) {
                        intElem = RimeDigits.getArabian(el.toUpperCase());
                        elems[1] = intElem;
                        if ((intElem<1)||(intElem>10)){
                            wrongInterval=true;
                        }
                        isDigit = true;
                    } else {
                        wrongType = true;
                    }
                }

                if (wrongType) {
                    throw new CustomException("Separate Exception: wrong type digit (not "+type.name()+") in second position: " + el);
                }

                if (!isDigit) {
                    throw new CustomException("Separate Exception: not a digit in second position: " + el);
                }

                if(wrongInterval){
                    throw new CustomException("Separate Exception: Digit<1 or >10: "+el);
                }

            }
            if(((countEl>3)&&(!firstMinus))||((countEl>4)&&(firstMinus))){
                throw new CustomException("Separate exception: you can use only one operation at once!:"+str);
            }

        }

        String returnString="";
        if(type==TypeRimeOrArabian.ARABIAN){
            returnString=elems[0].toString() + op + elems[1].toString();
        }else{//RIME
            returnString = RimeDigits.getRime(elems[0])+op+RimeDigits.getRime(elems[1]);
        }

        return returnString;
    }

    private boolean isArabianDigit(String el) {
        boolean res = false;
        try{
            int  i = Integer.parseInt(el);
            res=true;
        }catch (Exception e){
            ;
        }
        return res;
    }

    private boolean isRimeDigit(String el) {
        boolean res = false;
        String strPart;
        try{
            for(int i=0;i<el.length();i++){
                strPart=String.valueOf(el.toCharArray()[i]);
                RimeDigits.valueOf(strPart.toUpperCase());
                res = true;
            }
        }catch (Exception e){
            ;
        }
        return res;
    }

    public boolean isOp(String el){
        boolean res=false;
        switch (el) {
            case "+": res=true; break;
            case "-": res=true; break;
            case "*": res=true; break;
            case "/": res=true; break;
        }
        return res;
    }

}