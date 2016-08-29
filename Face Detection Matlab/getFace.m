function [img] = getFace(inImg,Theta1,Theta2,Theta3)
    temp_x = 1;
    temp_y = 1;
    img_w = size(inImg,2);
    img_h = size(inImg,1);
    mx_prob = 0;
    factor = 7.4;
    img = inImg;
    img = imadjust(img);
    img = imsharpen(img);
    for i = 1:2
        h = 48*factor;
        w = 40*factor;
        while (temp_y + h < img_h)
              temp_x = 1;
              while(temp_x + w < img_w)
                    %fflush(stdout);
                    %printf('%f %f\n',temp_x,temp_y);
                   im_resized = inImg(round(temp_y):round(temp_y) + round(h)-1, round(temp_x):round(temp_x) + round(w)-1);
                    im = imresize(im_resized,[48 40]);
                    im = extractHOGFeatures(im,'CellSize',[4 4]);
                    [p ,prob] = detect(im,Theta1,Theta2,Theta3);
                    if(prob > mx_prob)
                          mx_prob = prob;
                          img = im_resized;
                    end
                    temp_x = temp_x + 6;
              end
               temp_y = temp_y + .3*48;
        end
    factor =factor + .4;
    end
