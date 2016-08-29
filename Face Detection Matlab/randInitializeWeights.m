function W = randInitializeWeights(L_in, L_out)
E = 0.12;
W = rand(L_out,1+L_in)*2*E-E;
end
