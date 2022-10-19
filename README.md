
# Watermark Library

This library allows multi-line watermark on images




## How to get it 

add this into your projects build.gradle file

```kotlin 
  allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

add the dependency in your apps build.gradle file

```kotlin
    dependencies {
	        implementation 'com.github.safacet:Watermark:0.1.0'
	}
```
    
## Usage
Watermark instance can be created with an image file or bitmap

```kotlin
Watermark(requireContext(), file, photoText)
}
```
and the output can be a bitmap or to a file

  ```kotlin
val result = Watermark(requireContext(), file, photoText)
            .getBitmap()

Watermark(requireContext(), bitmap, photoText)
            .saveToFile(file)
}
```

You can change some properties of the watermark the default values are

```kotlin
Watermark(this@MainActivity, bitmap, text)
                    .setPosition(WatermarkPosition.BOTTOM_RIGHT)
                    .setTextColor(android.graphics.Color.WHITE)
                    .setTextSize(24) //as 24sp
                    .setTextAlign(Paint.Align.LEFT)
                    .setBackgroundColor(android.graphics.Color.BLACK) //Defaul is null
                    .setWidthRate(3) //Watermarks width rate over source's width
                    .setAlpha(90)
                    .saveToFile(file)
```

Position can be chosen custom
```kotlin
Watermark(this@MainActivity, bitmap, text)
                    .setPosition(31F, 31F)
                    .saveToFile(file)
```
## Licence

[MIT](https://github.com/safacet/Watermark/blob/main/LICENSE)

  
