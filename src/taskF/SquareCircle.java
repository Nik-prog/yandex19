package taskF;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

public class SquareCircle   {

	public static void main(String[] args) throws IOException {
		File inp= new File("inputF.txt");

		BufferedReader br = new BufferedReader(new FileReader(inp));
		
		String[] inpLine = br.readLine().split(" ");
		
		
		int Nsize = Integer.parseInt(inpLine[0]);
		double R = Double.parseDouble(inpLine[1]);

		double [][] Coor= new double[Nsize][2];
		
		for(int i=0;i<Nsize;i++) {
			inpLine = br.readLine().split(" ");
			
			Coor[i][0]=Double.parseDouble(inpLine[0]);
			Coor[i][1]=Double.parseDouble(inpLine[1]);
		}
		br.close();
		System.out.println("����");
		for(int i=0;i<Nsize;i++) {
			System.out.println(Coor[i][0]+" "+ Coor[i][1]);
		}
		double ExpectValue=Expectation(R, Coor);
		System.out.println("�����");
		System.out.format(Locale.US,"%1.10f%n", ExpectValue);
	}
	
	public static double Expectation(double Radius, double[][] Coord) {
		double ExpectValue=0.0;
		if(Radius<Math.sqrt(2)) {
			for (int i=0;i<Coord.length; i++) {
				double ExpectValuePlus=CrossArea(Radius,Coord[i][0],Coord[i][1]);
				ExpectValue=ExpectValue+ExpectValuePlus;
				System.out.println(ExpectValuePlus);
			}
			
		}
		else {
			ExpectValue=Coord.length;
		}
		
		return ExpectValue;
	}
	
	public static double CrossArea(double Radius, double CoordX, double CoordY) { //���������� ������� ����������� ���������� �������� � ���������� ������� R
		double CrossAreaValue=0.0;
		double LeftCoord=CoordX-Radius; //����
		double RightCoord=CoordX+Radius;//�����
		double BotCoord=CoordY-Radius; 	//���
		double TopCoord=CoordY+Radius;	//����
		double AreaCircle=CircleArea(Radius);//������� �������� ����������
		
		//�������� ����� � ��������
		double alfaRight=Math.acos((1-CoordX)/Radius);	
		double alfaTop=Math.acos((1-CoordY)/Radius);
		double alfaLeft=Math.acos((CoordX)/Radius);
		double alfaBot=Math.acos((CoordY)/Radius);
		
		
		if(LeftCoord>=0 && RightCoord <=1 && BotCoord >=0 && TopCoord <=1) { //���������� ������ ��������
			CrossAreaValue=AreaCircle;
		}

		
		else if(CoordY==0 || CoordY==1) {		//���������� �� �����. ������+������� �����

			if( LeftCoord>=0 && RightCoord <=1) {	//�� ���������� ���������. �����
				CrossAreaValue=AreaCircle/2;
			}
			else if(LeftCoord<0 && RightCoord>1) {	//���������� ����� � ������
				double dv1=Math.sqrt(CoordX*CoordX+1*1);
				double dv2=Math.sqrt((1-CoordX)*(1-CoordX)+1*1);
				
				if(Radius<=1) {
					CrossAreaValue=CircleAreaSec(Radius,(Math.PI-(alfaLeft+alfaRight)))+
							(CoordX*CoordX*Math.tan(alfaLeft))/2+((1-CoordX)*(1-CoordX)*Math.tan(alfaRight))/2;
				}
				if(Radius>1 && dv1<Radius && dv2<Radius) {	//�������� ��� ��������������� ����
					CrossAreaValue=1*1;	//����� ������� ����� ��������
				}
				else if(Radius>1 & dv1<Radius) {	//������� ����� ����
					double alfa=0;
					if(CoordY==0) alfa=alfaTop;
					else 		alfa=alfaBot;
					
					CrossAreaValue=CircleAreaSec(Radius,(Math.PI/2-(alfa+alfaRight)))+CoordX*1+
							(1*1*Math.tan(alfa))/2+((1-CoordX)*(1-CoordX)*Math.tan(alfaRight))/2;
				}
				else if(Radius>1 & dv2<Radius) {	//������� ������ ����
					double alfa=0;
					if(CoordY==0) alfa=alfaTop;
					else  		alfa=alfaBot;
					
					CrossAreaValue=CircleAreaSec(Radius,(Math.PI/2-(alfa+alfaLeft)))+(1-CoordX)*1+
							(CoordX*CoordX*Math.tan(alfaLeft))/2+(1*1*Math.tan(alfa))/2;
				}
				else {				//�� �������� ��� ��������������� ���� � ���� ����������� � ������������� ������
					if (CoordY==0) {
						CrossAreaValue=CircleAreaSec(Radius,(Math.PI/2-(alfaLeft+alfaTop)))
								+CircleAreaSec(Radius,(Math.PI/2-(alfaRight+alfaTop)))
								+1*1*Math.tan(alfaTop)
								+(CoordX*CoordX*Math.tan(alfaLeft))/2
								+((1-CoordX)*(1-CoordX)*Math.tan(alfaRight))/2;
					}
					else if(CoordY==1) {
						CrossAreaValue=CircleAreaSec(Radius,(Math.PI/2-(alfaLeft+alfaBot)))
								+CircleAreaSec(Radius,(Math.PI/2-(alfaRight+alfaBot)))
								+1*1*Math.tan(alfaBot)
								+(CoordX*CoordX*Math.tan(alfaLeft))/2
								+((1-CoordX)*(1-CoordX)*Math.tan(alfaRight))/2;
					}
				}
			}
			else if(LeftCoord<0) {			//���������� ������ �����
				CrossAreaValue=CircleAreaSec(Radius,Math.PI-alfaLeft)+
									(CoordX*CoordX*Math.tan(alfaLeft))/2;
			}
			else if(RightCoord>1) {			//���������� ������ ������
				CrossAreaValue=CircleAreaSec(Radius,Math.PI-alfaRight)+
								((1-CoordX)*(1-CoordX)*Math.tan(alfaRight))/2;
			}
			
		}
		
		else if (CoordX==0 || CoordX==1) { 			//���������� �� ����������� �����
			if (BotCoord >=0 && TopCoord <=1) {		//�� ���������� ���������� �����	
				CrossAreaValue=AreaCircle/2;
			}
			else if(BotCoord<0 && TopCoord>1) {	//���������� ����� � ������
				double dv1=Math.sqrt(CoordY*CoordY+1*1);	//���������� �� ������� ���������������� ����
				double dv2=Math.sqrt((1-CoordY)*(1-CoordY)+1*1);//��������� �� �������� ���������������� ����
				
				if(Radius<=1) {
					CrossAreaValue=CircleAreaSec(Radius,(Math.PI-(alfaTop+alfaBot)))+
							(CoordY*CoordY*Math.tan(alfaBot))/2+((1-CoordY)*(1-CoordY)*Math.tan(alfaTop))/2;
				}
				if(Radius>1 && dv1<Radius && dv2<Radius) {	//�������� ��� ��������������� ����
					CrossAreaValue=1*1;	//����� ������� ����� ��������
				}
				else if(Radius>1 & dv1<Radius) {	//������� ������ ������������� ����
					double alfa=0;
					if(CoordX==0) alfa=alfaRight;
					else 		alfa=alfaLeft;
					
					CrossAreaValue=CircleAreaSec(Radius,(Math.PI/2-(alfa+alfaTop)))+CoordY*1+
							(1*1*Math.tan(alfa))/2+((1-CoordY)*(1-CoordY)*Math.tan(alfaTop))/2;
				}
				else if(Radius>1 & dv2<Radius) {	//������� ������� ������������� ����
					double alfa=0;
					if(CoordX==0) alfa=alfaRight;
					else  		alfa=alfaLeft;
					
					CrossAreaValue=CircleAreaSec(Radius,(Math.PI/2-(alfa+alfaBot)))+(1-CoordY)*1+
							(CoordY*CoordY*Math.tan(alfaBot))/2+(1*1*Math.tan(alfa))/2;
				}
				else {				//�� �������� ��� ��������������� ���� � ���� ����������� � ������������� ������
					if (CoordX==0) {
						CrossAreaValue=CircleAreaSec(Radius,(Math.PI/2-(alfaRight+alfaBot)))
								+CircleAreaSec(Radius,(Math.PI/2-(alfaRight+alfaTop)))
								+1*1*Math.tan(alfaRight)
								+(CoordY*CoordY*Math.tan(alfaBot))/2
								+((1-CoordY)*(1-CoordY)*Math.tan(alfaTop))/2;
					}
					else if(CoordX==1) {
						CrossAreaValue=CircleAreaSec(Radius,(Math.PI/2-(alfaLeft+alfaBot)))
								+CircleAreaSec(Radius,(Math.PI/2-(alfaLeft+alfaTop)))
								+1*1*Math.tan(alfaLeft)
								+(CoordY*CoordY*Math.tan(alfaBot))/2
								+((1-CoordY)*(1-CoordY)*Math.tan(alfaTop))/2;
					}
				}
			}
			else if(BotCoord<0) {				//���������� ������ �����
				CrossAreaValue=CircleAreaSec(Radius,Math.PI-alfaBot)+
									(CoordY*CoordY*Math.tan(alfaBot))/2;
			}
			else if(TopCoord>1) {				//���������� ������ ������
				CrossAreaValue=CircleAreaSec(Radius,Math.PI-alfaTop)+
								((1-CoordY)*(1-CoordY)*Math.tan(alfaTop))/2;
			}
		}
//********************************************************************************************************		
		else {			//����� ���������� ������ ��������
			double dist1=Math.sqrt(CoordX*CoordX+CoordY*CoordY);				// ���������� �� ������� ������ ����
			double dist2=Math.sqrt(CoordX*CoordX+(1-CoordY)*(1-CoordY));		// ���������� �� �������� ������ ����
			double dist3=Math.sqrt((1-CoordX)*(1-CoordX)+(1-CoordY)*(1-CoordY));	// ���������� �� �������� ������� ����			
			double dist4=Math.sqrt((1-CoordX)*(1-CoordX)+CoordY*CoordY);		// ���������� �� ������� ������� ����

			if (Math.min(dist1, dist2)>Radius && Math.min(dist3, dist4)>Radius)  {//���� �� ��������
				//************************************************************
				//���������� 1 �����
				if( LeftCoord>=0 && RightCoord <=1) {	//�� ���������� ������. �����
					if(BotCoord<0) {
						CrossAreaValue=CircleAreaSec(Radius,2*Math.PI-2*alfaBot)+CoordY*CoordY*Math.tan(alfaBot);
					}
					else if (TopCoord>1) {
						CrossAreaValue=CircleAreaSec(Radius,2*Math.PI-2*alfaTop)+(1-CoordY)*(1-CoordY)*Math.tan(alfaTop);
					}
				}
																			
				if( BotCoord>=0 && TopCoord <=1) {	//�� ���������� ��������. �����
					if(LeftCoord<0) {
						CrossAreaValue=CircleAreaSec(Radius,2*Math.PI-2*alfaLeft)+CoordX*CoordX*Math.tan(alfaLeft);
					}
					else if (RightCoord>1) {
						CrossAreaValue=CircleAreaSec(Radius,2*Math.PI-2*alfaRight)+(1-CoordX)*(1-CoordX)*Math.tan(alfaRight);
					}
				}
				//*******************************************
				//���������� 2 �����
				if(BotCoord<0 && LeftCoord<0) { //������ ����� ����
					if(TopCoord <=1 && RightCoord<=1) {
						CrossAreaValue=AreaCircle
								-(CircleAreaSec(Radius,2*alfaBot)-CoordY*CoordY*Math.tan(alfaBot))
								-(CircleAreaSec(Radius,2*alfaLeft)-CoordX*CoordX*Math.tan(alfaLeft));
					}
				}
				if(TopCoord >1 && LeftCoord<0) { //������� ����� ����
					if(BotCoord>=0 && RightCoord<=1) {
						CrossAreaValue=AreaCircle
								-(CircleAreaSec(Radius,2*alfaTop)-(1-CoordY)*(1-CoordY)*Math.tan(alfaTop))
								-(CircleAreaSec(Radius,2*alfaLeft)-CoordX*CoordX*Math.tan(alfaLeft));
					}
				}
				
				if(TopCoord >1 && RightCoord>1) { //������� ������ ����
					if(BotCoord>=0 && LeftCoord>=0) {
						CrossAreaValue=AreaCircle
								-(CircleAreaSec(Radius,2*alfaTop)-(1-CoordY)*(1-CoordY)*Math.tan(alfaTop))
								-(CircleAreaSec(Radius,2*alfaRight)-(1-CoordX)*(1-CoordX)*Math.tan(alfaRight));
					}
				}
				if(BotCoord<0 && RightCoord>1) { //������ ������ ����
					if(TopCoord <=1 && LeftCoord>=0) {
						CrossAreaValue=AreaCircle
								-(CircleAreaSec(Radius,2*alfaBot)-CoordY*CoordY*Math.tan(alfaBot))
								-(CircleAreaSec(Radius,2*alfaRight)-(1-CoordX)*(1-CoordX)*Math.tan(alfaRight));
					}
				}
				//*********************************************
				//���������� 3 �����
				if(BotCoord<0 && LeftCoord<0 && TopCoord>1 && RightCoord<=1) { //��� ���� ���� 
					CrossAreaValue=AreaCircle
							-(CircleAreaSec(Radius,2*alfaBot)-CoordY*CoordY*Math.tan(alfaBot))
							-(CircleAreaSec(Radius,2*alfaLeft)-CoordX*CoordX*Math.tan(alfaLeft))
							-(CircleAreaSec(Radius,2*alfaTop)-(1-CoordY)*(1-CoordY)*Math.tan(alfaTop));
				}
				if(BotCoord>=0 && LeftCoord<0 && TopCoord>1 && RightCoord>1) {// ���� ���� �����
					CrossAreaValue=AreaCircle
							-(CircleAreaSec(Radius,2*alfaLeft)-CoordX*CoordX*Math.tan(alfaLeft))
							-(CircleAreaSec(Radius,2*alfaTop)-(1-CoordY)*(1-CoordY)*Math.tan(alfaTop))
							-(CircleAreaSec(Radius,2*alfaRight)-(1-CoordX)*(1-CoordX)*Math.tan(alfaRight));
				}
				if(BotCoord<0 && LeftCoord>=0 && TopCoord>1 && RightCoord>1) {//���� ����� ��� 
					CrossAreaValue=AreaCircle
							-(CircleAreaSec(Radius,2*alfaBot)-CoordY*CoordY*Math.tan(alfaBot))
							-(CircleAreaSec(Radius,2*alfaTop)-(1-CoordY)*(1-CoordY)*Math.tan(alfaTop))
							-(CircleAreaSec(Radius,2*alfaRight)-(1-CoordX)*(1-CoordX)*Math.tan(alfaRight));
				}
				if(BotCoord<0 && LeftCoord<0 && TopCoord<=1 && RightCoord>1) { //����� ��� ����
					CrossAreaValue=AreaCircle
							-(CircleAreaSec(Radius,2*alfaBot)-CoordY*CoordY*Math.tan(alfaBot))
							-(CircleAreaSec(Radius,2*alfaLeft)-CoordX*CoordX*Math.tan(alfaLeft))
							-(CircleAreaSec(Radius,2*alfaRight)-(1-CoordX)*(1-CoordX)*Math.tan(alfaRight));
				}
				//*********************************************
				//���������� 4 �����				
				if(BotCoord<0 && LeftCoord<0 && TopCoord>1 && RightCoord>1) { //
					CrossAreaValue=AreaCircle
							-(CircleAreaSec(Radius,2*alfaBot)-CoordY*CoordY*Math.tan(alfaBot))
							-(CircleAreaSec(Radius,2*alfaLeft)-CoordX*CoordX*Math.tan(alfaLeft))
							-(CircleAreaSec(Radius,2*alfaTop)-(1-CoordY)*(1-CoordY)*Math.tan(alfaTop))
							-(CircleAreaSec(Radius,2*alfaRight)-(1-CoordX)*(1-CoordX)*Math.tan(alfaRight));
				}
				
			}
			//*****************************************
			//������� ������ ����� ����
			else if(dist1<=Radius && dist2>Radius && dist3>Radius && dist4>Radius) {
				if(TopCoord<=1 && RightCoord<=1) {
					CrossAreaValue=CircleAreaSec(Radius,1.5*Math.PI-(alfaLeft+alfaBot))
							+CoordX*CoordY
							+CoordX*CoordX*Math.tan(alfaLeft)/2
							+CoordY*CoordY*Math.tan(alfaBot)/2;
				}
				else if(TopCoord>1 && RightCoord<=1) {
					CrossAreaValue=CircleAreaSec(Radius,Math.PI-(alfaTop+alfaBot))
							+CircleAreaSec(Radius,Math.PI/2-(alfaTop+alfaLeft))
							+CoordX*CoordY
							+CoordX*CoordX*Math.tan(alfaLeft)/2
							+CoordY*CoordY*Math.tan(alfaBot)/2
							+(1-CoordY)*(1-CoordY)*Math.tan(alfaTop);
				}
				else if (TopCoord<=1 && RightCoord>1) {
					CrossAreaValue=CircleAreaSec(Radius,Math.PI-(alfaLeft+alfaRight))
							+CircleAreaSec(Radius,Math.PI/2-(alfaBot+alfaRight))
							+CoordX*CoordY
							+CoordX*CoordX*Math.tan(alfaLeft)/2
							+CoordY*CoordY*Math.tan(alfaBot)/2
							+(1-CoordX)*(1-CoordX)*Math.tan(alfaRight);
				}
				else {
					CrossAreaValue=CircleAreaSec(Radius,Math.PI/2-(alfaTop+alfaLeft))
							+CircleAreaSec(Radius,Math.PI/2-(alfaTop+alfaRight))
							+CircleAreaSec(Radius,Math.PI/2-(alfaBot+alfaRight))
							+CoordX*CoordY
							+CoordX*CoordX*Math.tan(alfaLeft)/2
							+CoordY*CoordY*Math.tan(alfaBot)/2
							+(1-CoordY)*(1-CoordY)*Math.tan(alfaTop)
							+(1-CoordX)*(1-CoordX)*Math.tan(alfaRight);
				}			
			}
			//*****************************************
			//������� ������� ����� ����
			else if(dist1>Radius && dist2<=Radius && dist3>Radius && dist4>Radius) {
				if(BotCoord>=0 && RightCoord<=1) {
					CrossAreaValue=CircleAreaSec(Radius,1.5*Math.PI-(alfaLeft+alfaTop))
							+CoordX*(1-CoordY)
							+CoordX*CoordX*Math.tan(alfaLeft)/2
							+(1-CoordY)*(1-CoordY)*Math.tan(alfaTop)/2;
				}
				else if(BotCoord<0 && RightCoord<=1) {
					CrossAreaValue=CircleAreaSec(Radius,Math.PI-(alfaTop+alfaBot))
							+CircleAreaSec(Radius,Math.PI/2-(alfaBot+alfaLeft))
							+CoordX*(1-CoordY)
							+CoordX*CoordX*Math.tan(alfaLeft)/2
							+CoordY*CoordY*Math.tan(alfaBot)
							+(1-CoordY)*(1-CoordY)*Math.tan(alfaTop)/2;
				}
				else if (BotCoord>=0 && RightCoord>1) {
					CrossAreaValue=CircleAreaSec(Radius,Math.PI-(alfaLeft+alfaRight))
							+CircleAreaSec(Radius,Math.PI/2-(alfaTop+alfaRight))
							+CoordX*(1-CoordY)
							+CoordX*CoordX*Math.tan(alfaLeft)/2
							+(1-CoordY)*(1-CoordY)*Math.tan(alfaTop)/2
							+(1-CoordX)*(1-CoordX)*Math.tan(alfaRight);
				}
				else {
					CrossAreaValue=CircleAreaSec(Radius,Math.PI/2-(alfaBot+alfaLeft))
							+CircleAreaSec(Radius,Math.PI/2-(alfaTop+alfaRight))
							+CircleAreaSec(Radius,Math.PI/2-(alfaBot+alfaRight))
							+CoordX*(1-CoordY)
							+CoordX*CoordX*Math.tan(alfaLeft)/2
							+CoordY*CoordY*Math.tan(alfaBot)
							+(1-CoordY)*(1-CoordY)*Math.tan(alfaTop)/2
							+(1-CoordX)*(1-CoordX)*Math.tan(alfaRight);
				}			
			}			
			//*****************************************
			//������� ������� ������ ����
			else if(dist1>Radius && dist2>Radius && dist3<=Radius && dist4>Radius) {
				if(BotCoord>=0 && LeftCoord>=0) {
					CrossAreaValue=CircleAreaSec(Radius,1.5*Math.PI-(alfaRight+alfaTop))
							+(1-CoordX)*(1-CoordY)
							+(1-CoordX)*(1-CoordX)*Math.tan(alfaRight)/2
							+(1-CoordY)*(1-CoordY)*Math.tan(alfaTop)/2;
				}
				else if(BotCoord<0 && LeftCoord>=0) {
					CrossAreaValue=CircleAreaSec(Radius,Math.PI-(alfaTop+alfaBot))
							+CircleAreaSec(Radius,Math.PI/2-(alfaBot+alfaRight))
							+(1-CoordX)*(1-CoordY)
							+(1-CoordX)*(1-CoordX)*Math.tan(alfaRight)/2
							+CoordY*CoordY*Math.tan(alfaBot)
							+(1-CoordY)*(1-CoordY)*Math.tan(alfaTop)/2;
				}
				else if (BotCoord>=0 && LeftCoord<0) {
					CrossAreaValue=CircleAreaSec(Radius,Math.PI-(alfaLeft+alfaRight))
							+CircleAreaSec(Radius,Math.PI/2-(alfaTop+alfaLeft))
							+(1-CoordX)*(1-CoordY)
							+CoordX*CoordX*Math.tan(alfaLeft)
							+(1-CoordY)*(1-CoordY)*Math.tan(alfaTop)/2
							+(1-CoordX)*(1-CoordX)*Math.tan(alfaRight)/2;
				}
				else {
					CrossAreaValue=CircleAreaSec(Radius,Math.PI/2-(alfaBot+alfaLeft))
							+CircleAreaSec(Radius,Math.PI/2-(alfaTop+alfaLeft))
							+CircleAreaSec(Radius,Math.PI/2-(alfaBot+alfaRight))
							+(1-CoordX)*(1-CoordY)
							+CoordX*CoordX*Math.tan(alfaLeft)
							+CoordY*CoordY*Math.tan(alfaBot)
							+(1-CoordY)*(1-CoordY)*Math.tan(alfaTop)/2
							+(1-CoordX)*(1-CoordX)*Math.tan(alfaRight)/2;
				}			
			}			
			//*****************************************
			//������� ������ ������ ����
			else if(dist1>Radius && dist2>Radius && dist3>Radius && dist4<=Radius) {
				if(TopCoord<=1 && LeftCoord>=0) {
					CrossAreaValue=CircleAreaSec(Radius,1.5*Math.PI-(alfaRight+alfaBot))
							+(1-CoordX)*CoordY
							+(1-CoordX)*(1-CoordX)*Math.tan(alfaRight)/2
							+CoordY*CoordY*Math.tan(alfaBot)/2;
				}
				else if(TopCoord<=1 && LeftCoord<0) {
					CrossAreaValue=CircleAreaSec(Radius,Math.PI-(alfaLeft+alfaRight))
							+CircleAreaSec(Radius,Math.PI/2-(alfaBot+alfaLeft))
							+(1-CoordX)*CoordY
							+(1-CoordX)*(1-CoordX)*Math.tan(alfaRight)/2
							+CoordY*CoordY*Math.tan(alfaBot)/2
							+CoordX*CoordX*Math.tan(alfaLeft);
				}
				else if (TopCoord>1 && LeftCoord>=0) {
					CrossAreaValue=CircleAreaSec(Radius,Math.PI-(alfaTop+alfaBot))
							+CircleAreaSec(Radius,Math.PI/2-(alfaTop+alfaRight))
							+(1-CoordX)*CoordY
							+(1-CoordX)*(1-CoordX)*Math.tan(alfaRight)/2
							+CoordY*CoordY*Math.tan(alfaBot)/2
							+(1-CoordY)*(1-CoordY)*Math.tan(alfaTop);
				}
				else {
					CrossAreaValue=CircleAreaSec(Radius,Math.PI/2-(alfaBot+alfaLeft))
							+CircleAreaSec(Radius,Math.PI/2-(alfaTop+alfaLeft))
							+CircleAreaSec(Radius,Math.PI/2-(alfaTop+alfaRight))
							+(1-CoordX)*CoordY
							+CoordX*CoordX*Math.tan(alfaLeft)
							+CoordY*CoordY*Math.tan(alfaBot)/2
							+(1-CoordY)*(1-CoordY)*Math.tan(alfaTop)
							+(1-CoordX)*(1-CoordX)*Math.tan(alfaRight)/2;
				}			
			}
			//****************************************
			//�������� ��� ����
			//*****************************************
			//��� ������ ����
			else if(dist1<=Radius && dist2>Radius && dist3>Radius && dist4<=Radius) {
				if(TopCoord<=1) {
					CrossAreaValue=CircleAreaSec(Radius,Math.PI-(alfaLeft+alfaRight))
							+1*CoordY
							+CoordX*CoordX*Math.tan(alfaLeft)/2
							+(1-CoordX)*(1-CoordX)*Math.tan(alfaRight)/2;
				}
				else {
					CrossAreaValue=CircleAreaSec(Radius,Math.PI/2-(alfaLeft+alfaTop))
							+CircleAreaSec(Radius,Math.PI/2-(alfaTop+alfaRight))
							+1*CoordY
							+CoordX*CoordX*Math.tan(alfaLeft)/2
							+(1-CoordX)*(1-CoordX)*Math.tan(alfaRight)/2
							+(1-CoordY)*(1-CoordY)*Math.tan(alfaTop);
				}				
			}
			//*****************************************
			//��� ����� ����
			else if(dist1<=Radius && dist2<=Radius && dist3>Radius && dist4>Radius) {
				if(RightCoord<=1) {
					CrossAreaValue=CircleAreaSec(Radius,Math.PI-(alfaTop+alfaBot))
							+1*CoordX
							+CoordY*CoordY*Math.tan(alfaBot)/2
							+(1-CoordY)*(1-CoordY)*Math.tan(alfaTop)/2;
				}
				else {
					CrossAreaValue=CircleAreaSec(Radius,Math.PI/2-(alfaTop+alfaRight))
							+CircleAreaSec(Radius,Math.PI/2-(alfaBot+alfaRight))
							+1*CoordX
							+CoordY*CoordY*Math.tan(alfaBot)/2
							+(1-CoordY)*(1-CoordY)*Math.tan(alfaTop)/2
							+(1-CoordX)*(1-CoordX)*Math.tan(alfaRight);
				}				
			}
			//*****************************************
			//��� ������� ����
			else if(dist1>Radius && dist2<=Radius && dist3<=Radius && dist4>Radius) {
				if(BotCoord>=0) {
					CrossAreaValue=CircleAreaSec(Radius,Math.PI-(alfaLeft+alfaRight))
							+1*(1-CoordY)
							+CoordX*CoordX*Math.tan(alfaLeft)/2
							+(1-CoordX)*(1-CoordX)*Math.tan(alfaRight)/2;
				}
				else {
					CrossAreaValue=CircleAreaSec(Radius,Math.PI/2-(alfaBot+alfaLeft))
							+CircleAreaSec(Radius,Math.PI/2-(alfaBot+alfaRight))
							+1*(1-CoordY)
							+CoordX*CoordX*Math.tan(alfaLeft)/2
							+(1-CoordX)*(1-CoordX)*Math.tan(alfaRight)/2
							+CoordY*CoordY*Math.tan(alfaBot);
				}				
			}
			//*****************************************
			//��� ������ ����
			else if(dist1>Radius && dist2>Radius && dist3<=Radius && dist4<=Radius) {
				if(LeftCoord>=0) {
					CrossAreaValue=CircleAreaSec(Radius,Math.PI-(alfaTop+alfaBot))
							+1*(1-CoordX)
							+CoordY*CoordY*Math.tan(alfaBot)/2
							+(1-CoordY)*(1-CoordY)*Math.tan(alfaTop)/2;
				}
				else {
					CrossAreaValue=CircleAreaSec(Radius,Math.PI/2-(alfaBot+alfaLeft))
							+CircleAreaSec(Radius,Math.PI/2-(alfaTop+alfaLeft))
							+1*(1-CoordX)
							+CoordY*CoordY*Math.tan(alfaBot)/2
							+(1-CoordY)*(1-CoordY)*Math.tan(alfaTop)/2
							+CoordX*CoordX*Math.tan(alfaLeft);
				}				
			}
			//*********************************************************
			//����������� ������� 3 ����
			//*********************************************************
			//�������� ���� 1,2,4
			else if(dist1<=Radius && dist2<=Radius && dist3>Radius && dist4<=Radius) {
				CrossAreaValue=CircleAreaSec(Radius,Math.PI/2-(alfaTop+alfaRight))
						+(1*CoordX+(1-CoordX)*CoordY)
						+(1-CoordY)*(1-CoordY)*Math.tan(alfaTop)/2
						+(1-CoordX)*(1-CoordX)*Math.tan(alfaRight)/2;
			}
			//*********************************************************
			//�������� ���� 1,2,3
			else if(dist1<=Radius && dist2<=Radius && dist3<=Radius && dist4>Radius) {
				CrossAreaValue=CircleAreaSec(Radius,Math.PI/2-(alfaBot+alfaRight))
						+(1*CoordX+(1-CoordX)*(1-CoordY))
						+CoordY*CoordY*Math.tan(alfaBot)/2
						+(1-CoordX)*(1-CoordX)*Math.tan(alfaRight)/2;
			}
			//*********************************************************
			//�������� ���� 2,3,4
			else if(dist1>Radius && dist2<=Radius && dist3<=Radius && dist4<=Radius) {
				CrossAreaValue=CircleAreaSec(Radius,Math.PI/2-(alfaBot+alfaLeft))
						+(1*(1-CoordX)+CoordX*(1-CoordY))
						+CoordY*CoordY*Math.tan(alfaBot)/2
						+CoordX*CoordX*Math.tan(alfaLeft)/2;
			}
			//*********************************************************
			//�������� ���� 1,3,4
			else if(dist1<=Radius && dist2>Radius && dist3<=Radius && dist4<=Radius) {
				CrossAreaValue=CircleAreaSec(Radius,Math.PI/2-(alfaTop+alfaLeft))
						+(1*(1-CoordX)+CoordX*CoordY)
						+(1-CoordY)*(1-CoordY)*Math.tan(alfaTop)/2
						+CoordX*CoordX*Math.tan(alfaLeft)/2;
			}
			//��� ���� ��������
			else if (Math.min(dist1, dist2)<=Radius && Math.min(dist3, dist4)<=Radius) {
				CrossAreaValue=1.0;
			}			
		}		
		return CrossAreaValue;
	}	
	
	static double CircleArea(double radius) {
	    return Math.PI * (radius * radius);
	}
	static double CircleAreaSec(double radius,double angle) { //angle � ��������
	    return (radius* radius)*angle/2;
	}
	
	static double ArcLength(double radius) {
	    return 2*Math.PI * radius;
	}	
}
