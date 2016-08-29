function [p1 p2 p3 p4 p5 ] = sortProb(pointX,pointY,x,y,prob );

  for i=1: size(pointX, 2)-1
     for j = 1: size(pointX, 2)-i
        if (prob(j) > prob(j+1))
          temp=prob(j);
          prob(j)=prob(j+1);
          prob(j+1)=temp;
          temp=pointX(j);
          pointX(j)=pointX(j+1);
          pointX(j+1)=temp;
          temp=pointY(j);
          pointY(j)=pointY(j+1);
          pointY(j+1)=temp;
          temp=x(j);
          x(j)=x(j+1);
          x(j+1)=temp;
          temp=y(j);
          y(j)=y(j+1);
          y(j+1)=temp;
         
           end
     end
   end
   p1=pointX;
   p2=pointY;
   p3=x;
   p4=y;
   p5=prob;

end

