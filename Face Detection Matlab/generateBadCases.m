function[] = generateBadCases()
    files = getAllFiles('noface/not done');
    counter = 1;
    cnt = 0;
    for k=1:size(files,1)
       str = files(k);
       str = str{1};
       try
           img = imread(str);
           if size(img,1) > 600
               img = imresize(img,400/size(img,1));
           end
           if size(img,2) > 800
               img = imresize(img,500/size(img,2));
           end
           imshow(img);
           counter = counter +1;
           drawnow();
           cnt = cnt+ testHyp(img,false);
           fprintf('%d , badfaces : %d\n',counter,cnt);
           drawnow('update');
       catch
       end
    end
    close all;
    disp('Done.');


