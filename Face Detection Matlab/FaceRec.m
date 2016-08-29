clear ; close all;
load ('samples_cropped_2.mat');
%s = min(size(Q,1),size(Z,1));
Q = double(Q(1:end,:));
Z = double(Z(1:end,:));
%Z = Z(randperm(size(Z,1)),:);
samples = [Q(1:floor(.8*size(Q,1)), :) ; Z(1:floor(.8*size(Z,1)), :) ];
X = samples(:, 1:size(samples, 2)-1);
y = samples(:, end);
prediction = [Q(floor(.8*size(Q,1))+1:end, :) ; Z(floor(.8*size(Z,1))+1:end, :)];
prediction_out = prediction(:, size(prediction, 2));
prediction = prediction(:, 1:size(prediction, 2) - 1);

clear Q;
clear Z;

%sel = randperm(size(X, 1));
%sel = sel(1:50);
%displayData(X(sel, :),dimx,dimy);

lambda = 0.5;
[Theta1 Theta2 Theta3] = trainNN(X, y, lambda);
clear X;
clear y;
if(Theta3 == false)
    clear Theta3;
    [p h] = predict(prediction,Theta1, Theta2);
else
    [p h] = predict(prediction,Theta1, Theta2,Theta3 );
end
fprintf('\nTraining Set Accuracy: %f\n', mean(double(p == prediction_out)) * 100);
fprintf('\nNumber of mismatches: %d out of total %d \n No of wrong zeros : %d out of total zeros %d\n No of wrong ones : %d out of total ones %d\n', sum(p ~= prediction_out) ,size(p,1),sum( (p == 1) & (0 == prediction_out) ) , sum(0 == prediction_out),sum( (p == 0) & (1 == prediction_out) ) , sum(1 == prediction_out ));

% [prediction_out h]
if exist('Theta3','var')
    save theta_cropped.mat Theta1 Theta2 Theta3 dimx dimy
else
    save theta_cropped.mat Theta1 Theta2 dimx dimy
end
%displayData(Theta1(:, 2:end),dimx,dimy);