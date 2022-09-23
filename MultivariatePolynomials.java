/** datastructure final project - pardis ghavami **/
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
class Node{
	char var;
	float exp,coef;
	Node link , dlink  ,leftlink;
	boolean poly;
} 
class GUI extends JFrame {
	int i=0, x=10,y=40;
	JPanel jp = new JPanel();
	JPanel[] pan = new JPanel[60];
	JLabel[] txt1 = new JLabel[60]; //ceof
	JLabel[] txt2 = new JLabel[60]; //var
	JLabel[] txt3 = new JLabel[60]; //exp
	GUI(Node list) {
		super("MultivariatePolynomials");
		getContentPane().add(jp);
		setSize(1200, 700);
		setLocation(90, 90);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setVisible(true);
	    
	    draw(list);
	    repaint();
		revalidate();
    }
	public void draw(Node p) {
		while(p!= null){
			if(p.poly){	
				pan[i] = new JPanel(new GridLayout(1,3));
				if (!p.poly)
					txt1[i] = new JLabel("coef= "+p.coef,SwingConstants.CENTER);
				else
					txt1[i] = new JLabel("",SwingConstants.CENTER);
				txt2[i] = new JLabel(p.var+"",SwingConstants.CENTER);
				txt3[i] = new JLabel("exp= "+p.exp,SwingConstants.CENTER);
				pan[i].add(txt1[i]);
				pan[i].add(txt2[i]);
				pan[i].add(txt3[i]);
				txt1[i].setForeground(Color.black);
				txt2[i].setForeground(Color.black);
				txt3[i].setForeground(Color.black);
			    jp.add(pan[i]);
				jp.setLayout(null);
				pan[i].setBounds(x,y,160, 30);
				pan[i].setBackground(Color.lightGray);
				i++;
				y+=90;
				draw(p.dlink);
				
			}
			else {
				pan[i] = new JPanel(new GridLayout(1,3));
				txt1[i] = new JLabel("coef= "+p.coef,SwingConstants.CENTER);
				txt2[i] = new JLabel(p.var+"",SwingConstants.CENTER);
				txt3[i] = new JLabel("exp= "+p.exp,SwingConstants.CENTER);
				pan[i].add(txt1[i]);
				pan[i].add(txt2[i]);
				pan[i].add(txt3[i]);
				
				txt1[i].setForeground(Color.black);
				txt2[i].setForeground(Color.black);
				txt3[i].setForeground(Color.black);
			    jp.add(pan[i]);
				jp.setLayout(null);
				pan[i].setBounds(x,y,150, 30);
				pan[i].setBackground(Color.lightGray);
				y=40;
				x+=170;
			}
			p=p.link;
		}
		
		repaint();
		revalidate();
	}
}
public class MultivariatePolynomials{
	static Scanner input = new Scanner(System.in);
	public static void main(String[] args){
		System.out.println("*************************************************");
		System.out.println("*                ^_^ PICK ONE ^_^               *");
		System.out.println("*                                               *");
		System.out.println("*************************************************");
		System.out.println("*1. ADD                                         *");
		System.out.println("*2. MULT                                        *");
		System.out.println("*************************************************");
		String o=input.nextLine();
		System.out.println("\nENTER TWO POLYNOMIALS:");
		String str;
		str =input.nextLine();
		char[] c = new char[str.length()] ;
		char v='a';
		for(int i=0 ;i <str.length() ; i++){
			c[i] = str.charAt(i);
			if(c[i] >= v && c[i] <= 'z')
				v= c[i];
		}
		Node list = convert(c,0,v);
		//readpoly(list);
		//new GUI(list);
		System.out.println();
		v='a';
		str =input.nextLine();
		c = new char[str.length()];
		for(int i=0 ;i <str.length() ; i++){
			c[i] = str.charAt(i);
			if(c[i] >= v && c[i] <= 'z')
				v= c[i];
		}
		Node list2 = convert(c,0,v);
	//	readpoly(list2);
		System.out.println();
		if(Integer.valueOf(o)==1){
			Node list3 = add(list , list2);
			readpoly(list3);
			new GUI(list3);
		}
		else{
			System.out.println();
			Node list4= mult(list ,list2);
			readpoly(list4);
			new GUI(list4);
		}
	}
	public static void readpoly(Node list){
			Node p = list;
			while(p!= null){
				if(p.poly){
					System.out.print("+(");
					readpoly(p.dlink);
					System.out.print(")"+p.var+"^"+p.exp);
				}
				else
					System.out.print("+"+p.coef+p.var+"^"+p.exp);
				p=p.link;
			}
		}
	public static Node convert(char[] a, int l, char v){
		if(l>a.length)
			return null;
		int r = findMatch(a,l);
		Node p= new Node();
		p.var=v;
		char v2=(char)96;
		float exp=0f,coef;
		int i=0,l2=r+1;
		String str="";
		for(i=l+1 ; i<r; i++)
			if(a[i] <= 'z'&&a[i] > v2 )
				v2= a[i];
		
		if(v2>='a' && v2<='z')
			p.poly=true;
		else{
			v2 = v;
			p.poly=false;
		}
		if(!p.poly){
			for(i=l+1 ;i<r;++i)
					str = str+a[i];
			coef = Float.valueOf(str);
			str="";
			p.coef=coef;
			exp=0;
			if(r+1<a.length && a[r+1] == v){
				if(r+2 >=a.length||a[r+2] == '+' || a[r+2] ==')'){
					exp =1;
				}
				else{
					for(i=r+3 ;i<a.length && a[i] != ')' &&a[i] != '+' ;++i)
						str = str+a[i];
					exp = Float.valueOf(str);
					l2=i;
					str="";
				}
			}
			p.exp=exp;
			p.dlink=null;
		}
		
		if(r+1<a.length && a[r+1] == v){
			if(r+2 >=a.length||a[r+2] == '+' || a[r+2] ==')'){
				exp =1;
				l2=r+2;
			}
			else{
				for(i=r+3 ;i<a.length&& a[i]!='+' && a[i] != ')' ;++i)
					str = str+a[i];
				exp = Float.valueOf(str);
				str="";
				l2=i;
			}
		}
		p.exp = exp;
		if(l2+1<a.length&&a[l2]== '+'){
			p.link=convert(a,l2+1,v);
			if(p.link!=null)
				p.link.leftlink=p;
			
		}
		if(p.poly){
			p.dlink=convert(a,l+1,v2);
		}
		return p;
	}
	
	public static int findMatch(char[] a,int l){
		int k =l,o=0,c=0;//o is counting open parnetheses and c is counting closed parnetheses
		while(k<a.length){
			if(a[k] == '(')
				o++;
			if(a[k] == ')')
				c++;
			if(c==o)
				return k;
			k++;
		}
		return k;
	}
	
	public static Node add(Node list1 , Node list2){
		Node p=list1,q=list2,list=new Node(),l;
		if(p==null &&q==null){
			return null;
		}
		else if(p==null &&q!=null){
			return q;
		}
		else if(p!=null &&q==null){
			return p;
		}
		if(p.var != q.var){
			list.var=p.var >q.var ? p.var : q.var;
			list.exp=0;
			list.dlink=p.var >q.var ? q : p;
			list.link=p.var >q.var ? p : q;
			list.poly= list.dlink==null?false:true;
		}
		else if(p.var == q.var){
			if(!p.poly && !q.poly){
				if(p.exp == q.exp){
					list.coef = p.coef + q.coef;
					list.var = p.var;
					list.exp = p.exp;
					list.dlink=null;
					list.link=add(p.link,q.link);
				}
				else{
					list.var = p.var;
					list.exp=p.exp<q.exp?p.exp:q.exp;
					list.coef=p.exp<q.exp?p.coef:q.coef;
					list.dlink=p.exp<q.exp?p.dlink:q.link;
					list.link=add(p.exp<q.exp?p.link:p,p.exp<q.exp?q:q.link);
					
				}
				list.poly= list.dlink==null?false:true;
				
			}
			else if((!p.poly&&q.poly) ||(p.poly&& !q.poly )){
				list.var=p.var;
				list.exp=p.exp<=q.exp ? p.exp: q.exp;
				list.dlink=p.exp<q.exp?p.dlink:q.dlink;
				list.link=p.exp<q.exp?add(p.link,q):add(p,q.link);
				if(p.exp==q.exp){
					list.dlink=p.poly?p.dlink:q.dlink;
					list.link=add(p.link,q.link);
					l=list.dlink;
					while(l.link!=null){
						l=l.link;
					}
					l=l.link=new Node();
					l.var=list.dlink.var; l.exp=0; l.coef=p.poly?q.coef:p.coef; l.poly=false;
				}
					
				if(list.dlink==null)
					list.coef=p.poly?q.coef:p.coef;
				
				list.poly= list.dlink==null?false:true;
			}
			else{
				if(p.exp == q.exp){
					list.var = p.var;
					list.exp = p.exp;
					list.dlink=add(p.dlink,q.dlink);
					list.link=add(p.link,q.link);
				}
				else if(p.exp < q.exp){
					list.var = p.var;
					list.exp = p.exp;
					list.dlink=p.dlink;
					list.link=add(p.link,q);
				}
				else if(p.exp > q.exp){
					list.var = q.var;
					list.exp = q.exp;
					list.dlink=q.dlink;
					list.link=add(p,q.link);
				}
				list.poly=true;
			}
			
		}
		return list;
	}
	public static Node mult(Node list1 , Node list2){
		Node p=list1,q=list2,z,list=new Node();
		if(p==null&&q==null)
			return null;
		else if(p==null)
			return q;
		else if(q==null)
			return p;
		if(p.var != q.var){
			if(p.var>q.var){
				list.var=p.var;
				list.exp=p.exp;
				if(!p.poly){
					list.dlink=create(q);
					coefMult(list.dlink,p.coef);
				}
				else{
					list.dlink=mult(p.dlink,q);
				}
			}
			if(p.var<q.var){
				list.var=q.var;
				list.exp=q.exp;
				if(!q.poly){
					list.dlink=create(p);
					coefMult(list.dlink,q.coef);
				}
				else{
					list.dlink=mult(p,q.dlink);
				}
			}
			list.poly= list.dlink==null?false:true;
		}
		else{
			if(!p.poly && !q.poly){
				list.var= p.var;
				list.exp=p.exp+q.exp;
				list.coef = p.coef*q.coef;
				list.poly=false;
				z=q;
				if(z.link==null){
					if(p.link==null)
						list.link=null;
					else{
						while(z.leftlink !=null)
							z=z.leftlink;
						list.link=mult(p.link,z);
					}
				}
				else
					list.link=mult(p,q.link);
			
			}
			else if((!p.poly&&q.poly) ||(p.poly&& !q.poly )){
				list.var= p.var;
				list.exp=p.exp+q.exp;
				list.dlink=create(p.poly?p.dlink:q.dlink);
				coefMult(list.dlink,p.poly?q.coef:p.coef);
				list.poly=true;
				z=q;
				if(z.link==null){
					while(z.leftlink !=null)
						z=z.leftlink;
					if(p.link==null)
						list.link=null;
					else
						list.link=mult(p.link,z);
				}
				else
					list.link=mult(p,q.link);
				
			}
			else{
				list.var= p.var;
				list.exp=p.exp+q.exp;
				list.dlink=mult(p.dlink,q.dlink);
				z=q;
				if(z.link==null){
					while(z.leftlink !=null)
						z=z.leftlink;
					if(p.link==null)
						list.link=null;
					else
						list.link=mult(p.link,z);
				}
				else
					list.link=mult(p,q.link);
				
				list.poly=true;
				
			}
		}
		return list;
	}
	public static void coefMult(Node p , float c){
		if(p==null)
			return;
		coefMult(p.link,c);
		coefMult(p.dlink,c);
		if(!p.poly){
			p.coef = p.coef *c;
		}
		
	}
	public static Node create(Node p){
		if(p==null)
			return null;
		Node list = new Node();
		list.var=p.var;
		list.exp=p.exp;
		if(!p.poly){
			list.poly=false;
			list.coef=p.coef;
		}
		else
			list.poly=true;
		list.link=create(p.link);
		list.dlink=create(p.dlink);
		return list;
	}
	
}