function [counter] = testHyp(orgImg,displ)
  clear mxprop;
  %load('theta_cropped.mat');
  load('net_cropped.mat');
  if size(orgImg,3) == 1
      orgimg = orgImg;
  else
      orgimg = rgb2gray(orgImg);
  end
  n = NN.net;
  ratio = [dimx dimy];
  factor = .8;
  x_len = size(orgimg,2);
  y_len = size(orgimg,1);
  pointX = zeros(1000);
  pointY = zeros(1000);
  widths = zeros(1000);
  heights = zeros(1000);
  probs = zeros(1000);
  counter = 1;
  orgimg = imadjust(orgimg);
  orgimg = imsharpen(orgimg );
%   orgimg = edge(orgimg ,'canny');
  mxprop = 0;
  if(displ)
     imshow(orgimg);hold on;
%      rec = rectangle('Position', [0 0 ratio(1)*factor ratio(2)*factor] );
     drawnow();
  end
  for i=1 : 5
     x = ratio(1) * factor;
     y = ratio(2) * factor;
     temp_y = 1;
      fprintf('Stage : %d\n',i);
      drawnow('update');
    while (( temp_y + y ) <= y_len )
        temp_x = 1;
         mxprop_inrow = 0;
        while((temp_x + x) <= x_len)
         img = orgimg(ceil(temp_y):ceil(temp_y) + ceil(y)-1, ceil(temp_x):ceil(temp_x) + ceil(x)-1);
         img = imresize(img,[dimy dimx]);
         imgv = extractHOGFeatures(img,'CellSize',[4 4]);
%          if(exist('Theta3','var'))
%             [p prop] = detect(imgv,Theta1,Theta2,Theta3);
%          else
%             [p prop] = detect(imgv,Theta1,Theta2);
%          end
           out = n(imgv');
           prop = out(2);
         if(displ)
%                set(rec, 'Position', [ceil(temp_x) ceil(temp_y) ceil(x) ceil(y)]);   
%                drawnow();
         end
         mxprop = max(prop,mxprop);
         mxprop_inrow = max(prop,mxprop_inrow);
         if prop > .9
          if displ
              pointX(counter) = temp_x;
              pointY(counter) = temp_y;
              widths(counter) = x;
              heights(counter) = y;
              probs(counter) = prop;
              rectangle('Position', [ceil(temp_x) ceil(temp_y) ceil(x) ceil(y)]);
              drawnow();
          else
            imwrite(img,['cropped/badtest/' num2str(floor(rand*10000000000)) '.png']);
          end
          counter=counter+1;
          temp_x =temp_x + x/2;
          fprintf('%f\n',prop);
          drawnow('update');
         end
         temp_x = temp_x + (1-prop)*4;
        end
        temp_y = temp_y + .1*y;
    end
    factor =factor + .2;
  end
  counter = counter - 1;
  if displ
    disp(mxprop);
    close;
    displayImage(pointX(1:counter),pointY(1:counter),widths(1:counter),heights(1:counter),probs(1:counter),orgImg);
  end
end
