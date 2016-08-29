clear;
dimx = 36;
dimy = 36;

j = 1;
files = getAllFiles('cropped/cropped_ones_3');
size(files)
for k = 1 : size(files, 1)
      try
        img_str = files(k);
        img_str = img_str{1};
        
        image = imread(img_str);
        if(size(image,3) == 3)
            image = rgb2gray(image);
        end
        image = imresize(image,[dimy,dimx]);
        v = extractHOGFeatures(image,'CellSize',[3 3]);
        Q(j,:) = v;
        j = j+1;
        if(mod(j,20) == 0)
            fprintf('ones : %d\n',j);
            drawnow();
        end
      catch
      end
end
fprintf('Ones Loaded : %d\n',j-1);
Q = [Q ones(size(Q, 1), 1)];     % uncomment this if output is one
j = 1;
files = getAllFiles('cropped/cropped_zeros');
files = [files;getAllFiles('cropped/cropped_zeros2')];
files = [files;getAllFiles('cropped/badtest')];
files = files(randperm(size(files,1)),:);
size(files)
for k = 1 : size(files, 1)
    try
        img_str = files(k);
        img_str = img_str{1};
        
        image = imread(img_str);
        image = image(1:dimy,1:dimx);
        if(size(image,3) == 3)
            image = rgb2gray(image);
        end
        v = extractHOGFeatures(image,'CellSize',[3 3]);
        Z(j,:) = v;
        j = j + 1;
        if(mod(j,20) == 0)
            fprintf('zeros : %d\n',j);
            drawnow();
        end
    catch
        continue;
    end
end

fprintf('Zeros Loaded : %d\n',j-1);
Z = [Z zeros(size(Z, 1), 1)]; 
% uncomment this if output is zero

save 'samples_cropped_2.mat' Z Q dimx dimy                %saves final matrix in the main directory
FaceRec;
