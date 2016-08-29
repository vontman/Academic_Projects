function []= filterFaces(folder_name,folder_out)

  load('theta_cropped_backup.mat');
  files = getAllFiles(folder_name);
  counter = 1;
  size(files)
  for i=1:size(files,1)
      %disp([folder_out '/' num2str(counter) '.png']);
      
       try
              f = files(i);
              im = imread(f{1});
              counter = counter+ 1;
              im = getFace(im,Theta1,Theta2,Theta3);
              imwrite(imresize(im,[96 80]),[folder_out '/' num2str(floor(counter/500)) '/' num2str(counter) '.png'] );
              fprintf('%d\n',counter);
              drawnow();
      catch
      end
  end
  PrepareData;
end