function [ is_face prob] = detect(image ,Theta1,Theta2,Theta3)
 %resize
 image= image(:)';
 if(exist('Theta3','var'))
    [is_face prob]=predict(image,Theta1,Theta2,Theta3);
 else 
    [is_face prob]=predict(image,Theta1,Theta2);
 end

end

