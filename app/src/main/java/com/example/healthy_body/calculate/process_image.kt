package com.example.healthy_body.calculate

import android.graphics.Bitmap

class process_image(val bitmap : Bitmap ) {

    val textimg : String=""

fun process ():String{
  if(bitmap ==null)return "error"


   /* val localModel = FirebaseAutoMLLocalModel.Builder()
        .setAssetFilePath("manifest.json")
        .build()
    Log.e("localModel","${localModel}") // Success.

    val p0 = 0.0f
    val options = FirebaseVisionOnDeviceAutoMLImageLabelerOptions.Builder(localModel)
        .setConfidenceThreshold(p0)  // Evaluate your model in the Firebase console
        // to determine an appropriate value.
        .build()
    val labeler = FirebaseVision.getInstance().getOnDeviceAutoMLImageLabeler(options)


    val image= FirebaseVisionImage.fromBitmap(bitmap)

    labeler.processImage(image)
        .addOnSuccessListener { labels ->
            loop@ for (label in labels ) {
              textimg = label.text

                break@loop
            }
        }
        .addOnFailureListener { e ->

        }*/
    return "no_error"
}
}