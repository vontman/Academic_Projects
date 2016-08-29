function [] = displayImage(pointsX,pointsY,x,y,prob,image)
hold on;
 imshow(image);
 [pointsX pointsY x y prob]=sortProb(pointsX,pointsY,x,y,prob);
for i=1: size(pointsX, 2)
    flag=0;
   
    for j=i+1: size(pointsX, 2)
        if ((pointsX(j) < pointsX(i)) & (pointsX(j)+x(j) > pointsX(i)+x(i)) & ( pointsY(j)  < pointsY(i)) & (pointsY(j)+y(j) > pointsY(i)+y(i)))
  flag=1;
  break;
   end 
   hold on;
  areaof1=x(i)*y(i);
  areaof2=x(j)*y(j);
  inter12=rectint([pointsX(i) pointsY(i) x(i) y(i)],[pointsX(j) pointsY(j) x(j) y(j)]);
  if((inter12>areaof1/2) ||(inter12>areaof2/2))
      flag=1;
  end
   
    end
    if(flag==0)
          imwrite(imcrop(image,[pointsX(i) pointsY(i) x(i) y(i)]),['detected_faces/' num2str(floor(rand*10000000000)) '.png']);
          r=rectangle('Position', [pointsX(i) pointsY(i) x(i) y(i)] ); 
          set(r,'edgecolor','r');
          text(pointsX(i) ,pointsY(i)-3,sprintf('%.3f',prob(i)),'Color','blue');
    end
    
end

