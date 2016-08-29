function [J ,grad] = nnCostFunction(nn_params, ...
                                   input_layer_size, ...
                                   hidden_layer_size, ...
                                   hidden_layer_size2, ...
                                   X, y, lambda)
                                   
Theta1 = reshape(nn_params(1:hidden_layer_size * (input_layer_size + 1)), ...
                 hidden_layer_size, (input_layer_size + 1));
Theta2 = reshape(nn_params(1 + (hidden_layer_size * (input_layer_size + 1)):(hidden_layer_size * (input_layer_size + 1))+hidden_layer_size2 * (hidden_layer_size + 1)), ...
                 hidden_layer_size2, (hidden_layer_size + 1));
Theta3 = reshape(nn_params((1 + hidden_layer_size * (input_layer_size + 1)+(hidden_layer_size * (hidden_layer_size2 + 1))):end), ...
                 1, (hidden_layer_size2 + 1));

m = size(X, 1);

Theta1_grad = zeros(size(Theta1));
Theta2_grad = zeros(size(Theta2));
Theta3_grad = zeros(size(Theta3));

z2 = [ones(m,1) X] * Theta1';
a2 = sigmoid(z2);
z3 = [ones(m,1) a2] * Theta2';
a3 = sigmoid(z3);
z4 = [ones(m,1) a3] * Theta3';
a4 = sigmoid(z4);

reg_part = (lambda/2/m)*(sum(sum(Theta1(:,2:end).^2)) + sum(sum(Theta2(:,2:end).^2)) +sum(sum(Theta3(:,2:end).^2)) );
J = (-1/m) * sum(sum(y.*log(a4) + (1-y).*log(1-a4))) + reg_part;

s4 = a4-y;
tmp = (s4*Theta3);
s3 = tmp(:,2:end).*sigmoidGradient(z3);
tmp = (s3*Theta2);
s2 = tmp(:,2:end).*sigmoidGradient(z2);

Theta1_grad = s2'*[ones(size(X,1), 1) X]/m + [zeros(size(Theta1,1),1) (lambda/m)*Theta1(:,2:end)];
Theta2_grad = s3'*[ones(size(X,1), 1) a2]/m + [zeros(size(Theta2,1),1) (lambda/m)*Theta2(:,2:end)];
Theta3_grad = s4'*[ones(size(X,1), 1) a3]/m + [zeros(size(Theta3,1),1) (lambda/m)*Theta3(:,2:end)];

grad = [Theta1_grad(:) ; Theta2_grad(:);Theta3_grad(:)];


end
