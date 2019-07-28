package ms.fitcalculator.ui.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import ms.fitcalculator.R
import java.io.File
import java.io.FileOutputStream
import java.util.*

class ScreenShotFragment() : Fragment() {
    lateinit var image: Bitmap
    lateinit var file: File
    var isSaved: Boolean = false

    companion object {
        fun newInstance(image: Bitmap): Fragment {
            val args = Bundle()
            args.putParcelable("image", image)
            val fragment = ScreenShotFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.screen_shot_fragment, container, false)
        val screenView: ImageView? = view?.findViewById(R.id.screen_img)
        val btnSave: ImageView? = view?.findViewById(R.id.save_screen)
        val btnShare: ImageView? = view?.findViewById(R.id.share_screen)
        val btnCancel: ImageView? = view?.findViewById(R.id.cancel_screen)
        image = arguments!!.getParcelable<Bitmap>("image")
        screenView!!.setImageBitmap(image)
        btnSave!!.setOnClickListener {
            saveScreen(image)
            isSaved = true
        }
        btnShare!!.setOnClickListener {
            shareImage()
        }
        btnCancel!!.setOnClickListener {
            activity?.onBackPressed()
        }
        return view
    }

    private fun shareImage() {
        var share = Intent(Intent.ACTION_SEND)
        share.type = "image/*"
        if (isSaved == false)
            saveScreen(image)
        isSaved = true
        var uri = Uri.fromFile(file)
        share.putExtra(Intent.EXTRA_STREAM, uri)
        activity?.startActivity(Intent.createChooser(share, "Share Image!"))
    }

    private fun saveScreen(screen: Bitmap) {
        val celender = Calendar.getInstance()
        val hour = celender.get(Calendar.HOUR)
        val minute = celender.get(Calendar.MINUTE)
        val date = celender.get(Calendar.DATE)
        val name = hour.toString() + "_" + minute.toString() + "_" + date.toString()
        var root = Environment.getExternalStorageDirectory().toString()
        var directory = File(root + "/FitCalcualtor")
        directory.mkdirs()
        var fileName = "screen_shoot" + name + ".jpg"
        file = File(directory, fileName)
        if (file.exists())
            file.delete()
        try {
            val out = FileOutputStream(file)
            screen.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()
            Toast.makeText(
                context,
                getString(R.string.toast_saved_image) + fileName,
                Toast.LENGTH_LONG
            ).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}