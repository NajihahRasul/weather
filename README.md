# weather
Project Title :	Weather Application

About Project :	This is a mobile application to access current weather data by insert the latitude and longitude of any places on earth.

Language Use :	Java & XML

Platform Use:	Android Studio

API Use :	OpenWeatherMap
http://api.openweathermap.org/data/2.5/weather

# Screenshots :

![weatherapp](https://user-images.githubusercontent.com/39667828/47995697-1b272f00-e131-11e8-8ac7-69feb395720c.png)

How to use the application :

1.	Insert the longitude and latitude of any place that you want to know in the external control of your emulator.

2.	The weather output will be display at the emulator as shown in the screenshots.

# The parameter that the API able to receive :

1.	By geographic coordinates
API call:
api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}

# Parameters:
lat, lon coordinates of the location of your interest

# Examples of API calls:
http://api.openweathermap.org/data/2.5/weather?lat=3.2374&lon=101.6839&appid=2b4bf1af6880efadbdb70e5ba499ed40

# The responses that the API return (the JSON values) :

{"coord":{"lon":101.68,"lat":3.24},"weather":[{"id":201,"main":"Thunderstorm","description":"thunderstorm with rain","icon":"11d"}],"base":"stations","main":{"temp":299.18,"pressure":1007,"humidity":94,"temp_min":298.15,"temp_max":300.15},"visibility":8000,"wind":{"speed":1.5,"deg":150},"clouds":{"all":75},"dt":1541406600,"sys":{"type":1,"id":8132,"message":0.0049,"country":"MY","sunrise":1541372220,"sunset":1541415397},"id":1760605,"name":"Kepong","cod":200}

