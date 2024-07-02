package com.waigo.nowcoder.MyCode.Code02_meituan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
public class Main5 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String salaryType=null;
        String strSalary=null;
        String errorType=null;
        salaryType=br.readLine();
        strSalary=br.readLine();
        errorType=br.readLine();
        int ticket=0;
        if(salaryType!=null&&strSalary!=null&&errorType!=null)
        {
            try
            {
                int salary=Integer.parseInt(strSalary);
                String[] errorTypes=errorType.split(",");
                if(salaryType.equals("N"))
                {
                    if(errorTypes.length==1)
                    {
                        if(errorTypes[0].equals("L0"))
                        {
                            ticket=0;
                            System.out.println(ticket);
                        }
                        else if(errorTypes[0].equals("L1"))
                        {
                            ticket=(int)(0.02*salary);
                            System.out.println(ticket);
                        }
                        else if(errorTypes[0].equals("L2"))
                        {
                            ticket=(int)(0.04*salary);
                            System.out.println(ticket);
                        }

                        else
                        {
                            System.out.println("error");
                        }
                    }
                    else if(errorTypes.length==2)
                    {
                        if(errorTypes[0].equals("L1")&&errorTypes[1].equals("L2"))
                        {
                            ticket=(int)(0.06*salary);
                            System.out.println(ticket);
                        }
                    }
                    else
                    {
                        System.out.println("error");
                    }
                }
                else if(salaryType.equals("M"))
                {
                    if(errorTypes.length==1)
                    {
                        if(errorTypes[0].equals("L0"))
                        {
                            ticket=0;
                            System.out.println(ticket);
                        }
                        else if(errorTypes[0].equals("L1"))
                        {
                            ticket=(int)(0.04*salary);
                            System.out.println(ticket);
                        }
                        else if(errorTypes[0].equals("L2"))
                        {
                            ticket=(int)(0.08*salary);
                            System.out.println(ticket);
                        }

                        else
                        {
                            System.out.println("error");
                        }
                    }
                    else if(errorTypes.length==2)
                    {
                        if(errorTypes[0].equals("L1")&&errorTypes[1].equals("L2"))
                        {
                            ticket=(int)(0.12*salary);
                            System.out.println(ticket);
                        }
                    }
                    else
                    {
                        System.out.println("error");
                    }
                }

                else
                {
                    System.out.println("error");
                }
            }
            catch (Exception ex)
            {
                System.out.println("error");
            }
        }
        else
        {
            System.out.println("error");
        }
    }
}
