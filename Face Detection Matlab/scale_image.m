function pic_new = scale_image(pic, new_x, new_y) 

  oldSize = size(pic);                               %# Old image size
  %newSize = max(floor(scale_zoom.*oldSize(1:2)),1)  %# New image size
  newSize = [new_x new_y];
  newX = ((1:newSize(2))-0.5)./(newSize(2)/oldSize(2))+0.5;  %# New image pixel X coordinates
  newY = ((1:newSize(1))-0.5)./(newSize(1)/oldSize(1))+0.5;  %# New image pixel Y coordinates
  oldClass = class(pic);  %# Original image type
  pic = double(pic);      %# Convert image to double precision for interpolation

  if numel(oldSize) == 2  %# Interpolate grayscale image

    pic_new = interp2(pic,newX,newY(:),'cubic');

  else                    %# Interpolate RGB image

    pic_new = zeros([newSize 3]);  %# Initialize new image
    pic_new(:,:,1) = interp2(pic(:,:,1),newX,newY(:),'cubic');  %# Red plane
    pic_new(:,:,2) = interp2(pic(:,:,2),newX,newY(:),'cubic');  %# Green plane
    pic_new(:,:,3) = interp2(pic(:,:,3),newX,newY(:),'cubic');  %# Blue plane

  end

  pic_new = cast(pic_new,oldClass);  %# Convert back to original image type

end