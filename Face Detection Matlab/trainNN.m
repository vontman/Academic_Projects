function [Theta1 Theta2 Theta3] = trainNN(X, y, lambda)
load('theta_cropped.mat');

input_layer_size  = size(X,2);  % 92x112 Input Images of Digits
hidden_layer_size =25;   % 30 hidden units
hidden_layer_size2 =25;   % 30 hidden units
if(~exist('Theta1','var'))
    Theta1 = randInitializeWeights(input_layer_size, hidden_layer_size);
    Theta2 = randInitializeWeights(hidden_layer_size, hidden_layer_size2);
    Theta3 = randInitializeWeights(hidden_layer_size2, 1);
end
initial_nn_params = [Theta1(:) ; Theta2(:);Theta3(:)];


costFunction = @(t) nnCostFunction(t,input_layer_size,hidden_layer_size,hidden_layer_size, X, y, lambda);

options = optimset('MaxIter', 30, 'GradObj', 'on');

theta = fmincg(costFunction, initial_nn_params, options);

                
Theta1 = reshape(theta(1:hidden_layer_size * (input_layer_size + 1)), ...
                 hidden_layer_size, (input_layer_size + 1));
Theta2 = reshape(theta(1 + (hidden_layer_size * (input_layer_size + 1)):(hidden_layer_size * (input_layer_size + 1))+hidden_layer_size2 * (hidden_layer_size + 1)), ...
                 hidden_layer_size2, (hidden_layer_size + 1));
Theta3 = reshape(theta((1 + hidden_layer_size * (input_layer_size + 1)+(hidden_layer_size2 * (hidden_layer_size + 1))):end), ...
                 1, (hidden_layer_size2 + 1));

                 
end
