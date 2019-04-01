
#  First Name      : Khasha
#  Last Name       : Dehnad
#  Id              : 12345
#  purpose         : Accessing extrenal data and replacing missing value  
#                  : accessing data and perofrming EDA

remove(list=ls())

## Step 1 load the data
## changing ? to NA

#1-Load the "breast-cancer-wisconsin.data.csv" from canvas into R and perform the EDA analysis by:
bc<-
  read.csv("C://AIMS/Stevens_/2018_F_datamining/Raw_Data/breast-cancer-wisconsin.data.csv",
           na.strings = "?")


#  I.	Summarizing the each column (e.g. min, max, mean )
#II.	Identifying missing values
summary(bc)
is.na(bc)
missing<-bc[is.na(bc$F6),]

#III.	Replacing the missing values with the "mode" (most frequent value) of the column.
#install.packages("modeest")
library(modeest)
?mlv()

F6_mfv<-mlv(bc$F6, method = "mfv",na.rm = TRUE) 
str(F6_mfv)
F6_mfv$M

bc[is.na(bc$F6),"F6"]<-F6_mfv$M
summary(bc)
#IV.	Displaying the frequency table of Class vs. F6
table(Class=bc$Class,Sixth_Feture=bc$F6)

pairs(bc[c(2:5,11)], main = "Breast Cancer Graph",
      pch = 21, bg = c("red", "green")[factor(bc$Class)])

?hist
boxplot(bc[2:5])
boxplot(bc[6:9])
?paste
clnms<-colnames(bc)
paste("Breast Cancer column=" )
for(i in 2:3){
  hist(bc[[i]],main=paste("Breast Cancer column= ", clnms[i]))
}





#2- Delete all the objects from your environment. Reload the "breast-cancer-wisconsin.data.csv" from canvas into R. Remove any row with missing value in any of the columns.
bc<-
  read.csv("C://AIMS/Stevens_/2018_DataMining/Raw_Data/breast-cancer-wisconsin.data.csv",
           na.strings = "?")
?is.na()

bc2<-na.omit(bc)





