function g = sigmoidGradient(z)

z = sigmoid(z);
g = z.*(1-z);

end
