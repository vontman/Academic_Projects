function [p h] = predict(X,Theta1, Theta2,Theta3)
m = size(X, 1);
p = zeros(size(X, 1), 1);
h1 = sigmoid([ones(m, 1) X] * Theta1');
h2 = sigmoid([ones(m, 1) h1] * Theta2');
if(exist('Theta3','var'))
    h = sigmoid([ones(m, 1) h2] * Theta3');
else 
    h= h2;
end
p = h >= 0.6;
end
