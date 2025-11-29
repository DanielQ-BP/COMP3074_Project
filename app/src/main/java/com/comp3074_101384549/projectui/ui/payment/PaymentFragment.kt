package com.comp3074_101384549.projectui.ui.payment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.comp3074_101384549.projectui.R
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class PaymentFragment : Fragment() {

    private lateinit var paymentSheet: PaymentSheet

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_payment, container, false)

        paymentSheet = PaymentSheet(this) { result ->
            when (result) {
                is PaymentSheetResult.Completed ->
                    Toast.makeText(requireContext(), "Payment successful", Toast.LENGTH_SHORT).show()
                is PaymentSheetResult.Canceled ->
                    Toast.makeText(requireContext(), "Payment canceled", Toast.LENGTH_SHORT).show()
                is PaymentSheetResult.Failed ->
                    Toast.makeText(requireContext(), "Payment failed: ${result.error.message}", Toast.LENGTH_LONG).show()
            }
        }

        val payButton = view.findViewById<Button>(R.id.payButton)
        payButton.setOnClickListener {
            fetchClientSecretFromBackend()
        }

        return view
    }

    private fun fetchClientSecretFromBackend() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("http://10.0.2.2:4242/create-payment-intent")
            .post("{}".toRequestBody("application/json".toMediaType()))
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                if (!isAdded) return
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Network error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string().orEmpty()

                try {
                    val clientSecret = JSONObject(body).getString("clientSecret")

                    if (!isAdded) return
                    requireActivity().runOnUiThread {
                        presentPaymentSheet(clientSecret)
                    }

                } catch (e: Exception) {
                    if (!isAdded) return
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "Stripe error: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    private fun presentPaymentSheet(clientSecret: String) {
        val configuration = PaymentSheet.Configuration("ProjectUI")
        paymentSheet.presentWithPaymentIntent(clientSecret, configuration)
    }
}
