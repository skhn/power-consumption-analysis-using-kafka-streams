

Demo v0.1:

![IMG_7317](https://user-images.githubusercontent.com/18000907/129836909-5a7df008-1d99-45e8-9ab5-b4a9e84f5905.jpg)



https://user-images.githubusercontent.com/18000907/129839369-7e380cd9-2037-4e86-8d85-503589bb5a68.mov






# power-consumption-analysis-using-kafka-streams

This is an application created to showcase the use of kafka streams in the analysis of power consumption events for a particular household for a period of 47 months.

The dataset used for this proof of concept can be found in this link: https://archive.ics.uci.edu/ml/datasets/Individual+household+electric+power+consumption#

Features in the data set include:

- Date;                             dd/mm/yyyy
- Time;                             hh:mm:ss
- Global_active_power;              household global minute-averaged active power (in kilowatt)
- Global_reactive_power;            household global minute-averaged reactive power (in kilowatt)
- Voltage;                          minute-averaged voltage (in volt)
- Global_intensity;                 household global minute-averaged current intensity (in ampere)
- Sub_metering_1;                   kitchen, dishwasher, oven, microwave (watt-hour of active energy) 
- Sub_metering_2;                   laundry room, washer/dryer, refrigerator, light (watt-hour of active energy)
- Sub_metering_3                    electric water-heater, air-conditioner (watt-hour of active energy)


Useful notes:

- some data is missing: april 28th, 2007
- (global_active_power*1000/60 - sub_metering_1 - sub_metering_2 - sub_metering_3) represents the active energy consumed every minute (in watt hour) in the household by electrical equipment not measured in sub-meterings 1, 2 and 3.
