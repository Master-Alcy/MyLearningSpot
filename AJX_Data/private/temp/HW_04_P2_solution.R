
#  First Name      : Khasha
#  Last Name       : Dehnad
#  Id              : 12345
#  purpose         : Accessing extrenal data and replacing missing value  
#                  : accessing data and perofrming EDA

remove(list=ls())

## Step 1 load the data
## changing ? to NA
#3.2 Load the "breast-cancer-wisconsin.data.csv"
# from CANVAS

 bc<-
   read.csv("C://AIMS/Stevens_/2018_F_datamining/Raw_Data/breast-cancer-wisconsin.data.csv",
            colClasses=c("Class"="factor"),
            na.strings = "?")
 
#a.	Remove the rows with missing values
 bc_clean<-na.omit(bc)
 
#b.	Store every fifth record in a "test" dataset starting with the first record
  indx<-seq(1,nrow(bc_clean),5)
  test<-bc_clean[indx,]
#c.	Store the rest in the "training" dataset
  training<-bc_clean[-indx,]
#d.	Use knn with k=1 and classify the test dataset
  #install.packages("kknn")
  #Use the R library("kknn") 
  
  library(kknn)
  
  predict <- kknn(Class~., training[,-1], test, kernel="rectangular", k=1)
  fit <- fitted(predict)
#e.	Measure the performance of knn
  
  wrong<- (test[,11]!=fit)
  rate<-sum(wrong)/length(wrong)
  rate
  table(Test=test[,11],fit)
  
#f.	Repeat the above steps with k=2, k=5, k=10.
for(i in c(1:30)){
  predict <- kknn(Class~., training[,-1], test, kernel="rectangular", k=i)
  fit <- fitted(predict)
   wrong<- (test[,11]!=fit)
  rate<-sum(wrong)/length(wrong)
  rate
  print(rate)
}
 
