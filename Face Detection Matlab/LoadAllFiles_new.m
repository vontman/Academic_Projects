clear;
dimx = 84;
dimy = 96;

j = 1;
files = getAllFiles('CroppedYale');
size(files)
for k = 1 : size(files, 1)
     try
        img_str = files(k);
        img_str = img_str{1};
        
        image = imread(img_str);
        image = imresize(image,[dimy,dimx]);
        v = extractHOGFeatures(image,'CellSize',[6 6]);
        Q(j,:) = v;
        j = j + 1;
        if mod(j-1,50) == 0
            drawnow('update');
            fprintf('ones : %d\n',j);
        end
     catch
     end
end
Q = [Q ones(size(Q, 1), 1)];     % uncomment this if output is one
j = 1;
files = getAllFiles('zeros_random');
files = [files;getAllFiles('badtest')];
size(files)
for k = 1 : size(files, 1)
%     if j > 12000
%         break;
%     end
    try
        img_str = files(k);
        img_str = img_str{1};
        
        image = imread(img_str);
        image = imresize(image,[dimy,dimx]);
        if(size(image,3) == 3)
            image = rgb2gray(image);
        end
        
        v = extractHOGFeatures(image,'CellSize',[6 6]);
        Z(j,:) = v;
        if mod(j,50) == 0
            drawnow('update');
            fprintf('zeros : %d\n',j);
        end
        j = j + 1;
    catch
        continue;
    end
end

Z = [Z zeros(size(Z, 1), 1)]; 
% uncomment this if output is zero

save 'samples_yale.mat' Z Q dimx dimy                %saves final matrix in the main directory
FaceRec;
