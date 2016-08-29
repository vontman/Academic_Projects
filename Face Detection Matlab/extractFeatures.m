clear;


j = 1;
files = getAllFiles('cropped/cropped_ones');
size(files)
rec = [14 24 10 10];
for k = 1 : size(files, 1)
%       try
        img_str = files(k);
        img_str = img_str{1};
        
        image = imread(img_str);
        if(size(image,3) == 3)
            image = rgb2gray(image);
        end
        image = imcrop(image,rec);
        imwrite(image,['cropped/cropped_nose/' num2str(j) '.jpg']);
        j = j+1;
        if(mod(j,20) == 0)
            fprintf('%d\n',j);
            drawnow();
        end
%       catch
%       end
end

