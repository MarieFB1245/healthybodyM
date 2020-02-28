package com.example.healthy_body.calculate

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.automl.FirebaseAutoMLLocalModel
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.label.FirebaseVisionOnDeviceAutoMLImageLabelerOptions

class process_image(val bitmap : Bitmap ) {



fun process (callback: (namefood:String) -> Unit){
    Log.e("bitmap","${bitmap}")
  if(bitmap !=null){
      val localModel = FirebaseAutoMLLocalModel.Builder()
          .setAssetFilePath("manifest.json")
          .build()
      Log.e("localModel","${localModel}") // Success.

      val p0 = 0.5f
      val options = FirebaseVisionOnDeviceAutoMLImageLabelerOptions.Builder(localModel)
          .setConfidenceThreshold(p0)  // Evaluate your model in the Firebase console
          // to determine an appropriate value.
          .build()
      val labeler = FirebaseVision.getInstance().getOnDeviceAutoMLImageLabeler(options)


      val image= FirebaseVisionImage.fromBitmap(bitmap)

      labeler.processImage(image)
          .addOnSuccessListener { labels ->
              loop@ for (label in labels ){
                  val namefood = label.text
                  val PERCEN = label.confidence
                  Log.e("name","${namefood}")
                  Log.e("name","${PERCEN}")
callback.invoke(namefood)

                  break@loop
              }
          }
          .addOnFailureListener { e ->

          }
  }




}
}