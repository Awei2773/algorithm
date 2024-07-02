package com.waigo.offer;

import java.util.HashMap;
import java.util.Map;

/**
 * author waigo
 * create 2021-09-23 11:42
 */
public class T53_isNumeric {
    public boolean isNumeric (String str) {
        //初始化好各个状态的迁移
        Map<State,Map<Option,State>> transfer = new HashMap<>();
        //1.初始状态的迁移
        Map<Option,State> initialTransfer = new HashMap<>();
        initialTransfer.put(Option.OPTION_SPACE,State.STATE_INIT);//前置空格
        initialTransfer.put(Option.OPTION_SIGN,State.STATE_INTEGER_SIGN);//整数正负号
        initialTransfer.put(Option.OPTION_NUMBER,State.STATE_INTEGER);//整数
        initialTransfer.put(Option.OPTION_POINT,State.STATE_POINT_WITHOUT_LEFT);//小数点
        transfer.put(State.STATE_INIT,initialTransfer);
        //2.符号位的迁移
        Map<Option,State> intSignTransfer = new HashMap<>();
        intSignTransfer.put(Option.OPTION_NUMBER,State.STATE_INTEGER);//整数
        intSignTransfer.put(Option.OPTION_POINT,State.STATE_POINT_WITHOUT_LEFT);//小数点，左无
        transfer.put(State.STATE_INTEGER_SIGN,intSignTransfer);
        //3.整数的迁移
        Map<Option,State> intTransfer = new HashMap<>();
        intTransfer.put(Option.OPTION_NUMBER,State.STATE_INTEGER);
        intTransfer.put(Option.OPTION_POINT,State.STATE_FRACTION);
        intTransfer.put(Option.EXP,State.STATE_EXP);//整数直接到指数
        intTransfer.put(Option.OPTION_SPACE,State.STATE_END);//结束状态
        transfer.put(State.STATE_INTEGER,intTransfer);
        //4.小数点左整数
        Map<Option,State> pointTransfer = new HashMap<>();
        pointTransfer.put(Option.OPTION_NUMBER,State.STATE_FRACTION);
        transfer.put(State.STATE_POINT,pointTransfer);
        //5.小数点左无整数
        Map<Option,State> pointWithoutLeftTransfer = new HashMap<>();
        pointWithoutLeftTransfer.put(Option.OPTION_NUMBER,State.STATE_FRACTION);
        transfer.put(State.STATE_POINT_WITHOUT_LEFT,pointWithoutLeftTransfer);
        //6.小数部分
        Map<Option,State> fractionTransfer = new HashMap<>();
        fractionTransfer.put(Option.OPTION_NUMBER,State.STATE_FRACTION);
        fractionTransfer.put(Option.EXP,State.STATE_EXP);
        fractionTransfer.put(Option.OPTION_SPACE,State.STATE_END);
        transfer.put(State.STATE_FRACTION,fractionTransfer);
        //7.指数e,E
        Map<Option,State> expTransfer = new HashMap<>();
        expTransfer.put(Option.OPTION_SIGN,State.STATE_EXP_SIGN);
        expTransfer.put(Option.OPTION_NUMBER,State.STATE_EXP_POW);
        transfer.put(State.STATE_EXP,expTransfer);
        //8.指数符号
        Map<Option,State> expSignTransfer = new HashMap<>();
        expSignTransfer.put(Option.OPTION_NUMBER,State.STATE_EXP_POW);
        transfer.put(State.STATE_EXP_SIGN,expSignTransfer);
        //9.指数数值
        Map<Option,State> expPowTransfer = new HashMap<>();
        expPowTransfer.put(Option.OPTION_NUMBER,State.STATE_EXP_POW);
        expPowTransfer.put(Option.OPTION_SPACE,State.STATE_END);
        transfer.put(State.STATE_EXP_POW,expPowTransfer);
        //10.结尾空格
        Map<Option,State> endTransfer = new HashMap<>();
        endTransfer.put(Option.OPTION_SPACE,State.STATE_END);
        transfer.put(State.STATE_END,endTransfer);
        State curState = State.STATE_INIT;
        for(int i = 0;i<str.length();i++){
            Option curOption = getOption(str.charAt(i));
            Map<Option, State> wantOptions = transfer.get(curState);
            if(curOption==Option.ERROR||!wantOptions.containsKey(curOption)){
                return false;
            }else{
                curState = wantOptions.get(curOption);//状态迁移
            }
        }
        return curState == State.STATE_INTEGER||curState==State.STATE_FRACTION||curState==State.STATE_EXP_POW||curState==State.STATE_END;



    }

    private Option getOption(char cur) {
        if(Character.isDigit(cur)) return Option.OPTION_NUMBER;
        switch (cur){
            case ' ':
                return Option.OPTION_SPACE;
            case '+':
            case '-':
                return Option.OPTION_SIGN;
            case '.':
                return Option.OPTION_POINT;
            case 'e':
            case 'E':
                return Option.EXP;
            default:
                return Option.ERROR;
        }
    }

    enum State{
        //1.初始状态
        STATE_INIT,
        //2.整数部分正负号
        STATE_INTEGER_SIGN,
        //3.整数部分
        STATE_INTEGER,
        //4.左有整数的小数点
        STATE_POINT,
        //5.左无整数的小数点
        STATE_POINT_WITHOUT_LEFT,
        //6.小数部分
        STATE_FRACTION,
        //7.指数符号
        STATE_EXP,
        //8.指数正负号
        STATE_EXP_SIGN,
        //9.指数幂次
        STATE_EXP_POW,
        //10.结尾空格
        STATE_END;
    }
    enum Option{
        //1.添加空格操作
        OPTION_SPACE,
        //2.正负号
        OPTION_SIGN,
        //3.一个数
        OPTION_NUMBER,
        //4.小数点
        OPTION_POINT,
        //5.E或者e
        EXP,
        //6.错误操作
        ERROR;
    }

    public static void main(String[] args) {
        System.out.println(new T53_isNumeric().isNumeric(".1"));
    }
}
