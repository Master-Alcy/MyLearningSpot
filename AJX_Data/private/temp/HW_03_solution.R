#################################################
#  Company    : Stevens 
#  Project    : R Bootcamp 
#  Purpose    : subset
#  First Name  : Khasha
#  Last Name  : Dehnad
#  Id			    : 12345
#  Date       :
#  Comments   :
rm(list=ls())

#################################################
##   Step:
##    [[]]
##     []
##
##
##
######################
bc<-
  read.csv("C://AIMS/Stevens_/2018_F_datamining/Raw_Data/breast-cancer-wisconsin.data.csv",
           na.strings = "?")
data("bc")
View(bc)
?sample

 
idx<-sample(nrow(bc),as.integer(.75*nrow(bc)))

training<-iris[idx,]
test<-iris[-idx,]

idx<-seq(1,nrow(iris),5)

training<-iris[-idx,]
test<-iris[idx,]