package com.senai.vsconnect_kotlin.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.senai.vsconnect_kotlin.adapters.ListaTrilhaAdapter
import com.senai.vsconnect_kotlin.apis.EndpointInterface
import com.senai.vsconnect_kotlin.apis.RetrofitConfig
import com.senai.vsconnect_kotlin.databinding.FragmentListaTrilhasBinding
import com.senai.vsconnect_kotlin.models.Servico
import com.senai.vsconnect_kotlin.models.Trilha
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaTrilhasFragment : Fragment() {

    private val clienteRetrofit = RetrofitConfig.obterInstanciaRetrofit()

    private val endpoints = clienteRetrofit.create(EndpointInterface::class.java)

    private var _binding: FragmentListaTrilhasBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentListaTrilhasBinding.inflate(inflater, container, false)

        val root: View = binding.root

        // organiza os itens da Recycler em ordem vertical, sendo um debaixo do outro
        binding.recyclerTrilhas.layoutManager = LinearLayoutManager(requireContext())


        // Configurar o adaptador e o OnItemClickListener
        val adapter = ListaTrilhaAdapter(requireContext(), emptyList())

        binding.recyclerTrilhas.adapter = adapter

        adapter.setOnItemClickListener(object : ListaTrilhaAdapter.OnItemClickListener {
            override fun onItemClick(link: String) {
                // Lidar com o clique do item aqui, por exemplo, abrir uma nova atividade ou URL
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                startActivity(intent)
            }
        })

        endpoints.listarTrilhas().enqueue(object : Callback<List<Trilha>> {

            override fun onResponse(call: Call<List<Trilha>>, response: Response<List<Trilha>>) {
                val trilhas = response.body()

                adapter.atualizarDados(trilhas.orEmpty())
            }

            override fun onFailure(call: Call<List<Trilha>>, t: Throwable) {
                println("Falha na requisição: ${t.message}")
            }

        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}