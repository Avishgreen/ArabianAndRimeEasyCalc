package Calc;

public enum RimeDigits {
    I(1),IV(4),V(5),IX(9),X(10),XL(40),L(50),
    XC(90),C(100),CD(400),D(500),CM(900),M(1000);

    private int arabian;

    RimeDigits(int arabianVal){
        this.arabian = arabianVal;
    }

    public static int getArabian(String rimStr) {
        int result=0,rimCount=12,indx=0,ordinal=0,nextOrdinal=0,val=0,arab=0;
        String letter="",nextLetter="";
        RimeDigits rime;
        while (true){
            letter=String.valueOf(rimStr.toCharArray()[indx]);
            if(indx+1<rimStr.length()){
                nextLetter=String.valueOf(rimStr.toCharArray()[indx]);
                nextOrdinal=RimeDigits.valueOf(nextLetter).ordinal();
            }else{
                nextLetter="";
                nextOrdinal=0;
            }

            rime = RimeDigits.valueOf(letter);
            ordinal=rime.ordinal();
            arab=rime.getArabian();
            if((ordinal<=rimCount)&&(ordinal<=nextOrdinal)){
                val+=arab;
            }else{
                val=arab-val;
                rimCount--;
                indx++;//skip next digit because already used it here
            }
            indx++;
            if(indx>=rimStr.length()){
                break;
            }
            if(rimCount<0){
                break;
            }
        }

        result=val;
        return result;
    }

    public int getArabian() {
        return arabian;
    }

    public static String getRime(int arabian){
//           https://trimstroy.com/raznoye/printsip-perevoda-arabskikh-chisel-v-rimskiye/
        String returnStr="";
        boolean isPlus=true;
        int count=0, rimCount=12, discount=0, res=0;

        if(arabian<0){arabian=-arabian;isPlus=false;}
        if(arabian>3999){
            returnStr="number is more then 3999: "+Integer.toString(arabian);
        }else{
            while (true){
                if(count>2){
                    count=0; rimCount--;
                }

                if(rimCount<0){
                    break;
                }

                discount=RimeDigits.values()[rimCount].getArabian();
                res=arabian-discount;

                if(res>=0){
                    count++;
                    arabian=res;
                    returnStr+=RimeDigits.values()[rimCount].name();
                    if(count>2){
                        rimCount--;
                        count=0;
                    }
                }else{
                    rimCount--;
                    count=0;
                }

            }
        }

        if(!isPlus){
            returnStr="-"+returnStr;
        }
        return returnStr;
    }
}
