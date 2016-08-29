clear;
dimx = 40;
dimy = 48;

j = 1;
files = getAllFiles('faces8');
size(files)
for k = 1 : size(files, 1)
      try
        img_str = files(k);
        img_str = img_str{1};
        
        image = imread(img_str);
        if(size(image,3) == 3)
            image = rgb2gray(image);
        end
        image = imadjust(image);
        if (mod(j,5) == 1)
            im2 = imrotate(image,3);
        elseif (mod(j,5) == 2)
            im2 = imrotate(image,-3);
        elseif (mod(j,5) == 3)
            im2 = imrotate(image,6);
        elseif (mod(j,5) == 4)
            im2 = imrotate(image,-6);
        end
        im2 = imcrop(im2,[(size(im2,2)-size(image,2))/2+1 (size(im2,1)-size(image,1))/2+1 size(image,2) size(image,1)]);
        im2 = imresize(im2,[dimy,dimx]);
        im2 = imsharpen(im2);
        imwrite(im2,['cropped/cropped_ones_2/' num2str(j) '.png']);
        j = j + 1;
        if mod(j-1,20) == 0
            drawnow('update');
            fprintf('ones : %d\n',j);
        end
      catch
      end
end
j = 1;
files = getAllFiles('zeros_random');
files = files(randperm(size(files,1)),:);
size(files)
for k = 1 : 0
     if j > 12000
         break;
     end
    try
        img_str = files(k);
        img_str = img_str{1};
        
        image = imread(img_str);
        image = imresize(image,[dimy,dimx]);
        if(size(image,3) == 3)
            image = rgb2gray(image);
        end
        image = imadjust(image);
        image = imsharpen(image);
        imwrite(image,['cropped/cropped_zeros/' num2str(j) '.png']);
        if mod(j,20) == 0
            drawnow('update');
            fprintf('zeros : %d\n',j);
        end
        j = j + 1;
    catch
        continue;
    end
end
LoadData