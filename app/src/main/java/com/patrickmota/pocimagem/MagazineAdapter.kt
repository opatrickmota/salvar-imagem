package com.patrickmota.pocimagem

import android.R
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.patrickmota.pocimagem.databinding.MagazineListBinding

class MagazineAdapter(
    private val onItemClicked: (imageUrl: String) -> Unit,
    private val onSharedImage: (imageUrl: String) -> Unit
) : RecyclerView.Adapter<MagazineAdapter.ViewHolder>() {

    class ViewHolder(val binding: MagazineListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            MagazineListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(magazines[position]) {
                binding.apply {

                    Glide
                        .with(binding.root)
                        .load(imageUrl)
                        .placeholder(R.color.darker_gray)
                        .into(magazineImage)

                    magazineText.text = text

                    downloadMagazine.setOnClickListener {
                        onItemClicked(imageUrl)
                    }

                    shareMagazine.setOnClickListener {
                        onSharedImage(imageUrl)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = magazines.size
}

data class Magazine(
    val imageUrl: String,
    val text: String
)

val magazines = listOf(
    Magazine(
        "https://static.natura.com.br/static/socialselling/07072023/COMPARTILHE_COM_O_ESPACO_DIGITAL_Peca_generica_2_07072023.jpeg",
        "Kit todo dia, nutrição e inteligência corporal"
    ),
    Magazine(
        "https://static.natura.com.br/static/socialselling/07072023/COMPARTILHE_COM_O_ESPACO_DIGITAL_Peca_generica_2_07072023.jpeg",
        "Kit todo dia, nutrição e inteligência corporal"
    ),
    Magazine(
        "https://static.natura.com.br/static/socialselling/07072023/COMPARTILHE_COM_O_ESPACO_DIGITAL_Peca_generica_2_07072023.jpeg",
        "Kit todo dia, nutrição e inteligência corporal"
    ),
    Magazine(
        "https://static.natura.com.br/static/socialselling/07072023/COMPARTILHE_COM_O_ESPACO_DIGITAL_Peca_generica_2_07072023.jpeg",
        "Kit todo dia, nutrição e inteligência corporal"
    ),
    Magazine(
        "https://static.natura.com.br/static/socialselling/07072023/COMPARTILHE_COM_O_ESPACO_DIGITAL_Peca_generica_2_07072023.jpeg",
        "Kit todo dia, nutrição e inteligência corporal"
    ),
    Magazine(
        "https://static.natura.com.br/static/socialselling/07072023/COMPARTILHE_COM_O_ESPACO_DIGITAL_Peca_generica_2_07072023.jpeg",
        "Kit todo dia, nutrição e inteligência corporal"
    ),
    Magazine(
        "https://static.natura.com.br/static/socialselling/07072023/COMPARTILHE_COM_O_ESPACO_DIGITAL_Peca_generica_2_07072023.jpeg",
        "Kit todo dia, nutrição e inteligência corporal"
    ),
    Magazine(
        "https://static.natura.com.br/static/socialselling/07072023/COMPARTILHE_COM_O_ESPACO_DIGITAL_Peca_generica_2_07072023.jpeg",
        "Kit todo dia, nutrição e inteligência corporal"
    ),
    Magazine(
        "https://static.natura.com.br/static/socialselling/07072023/COMPARTILHE_COM_O_ESPACO_DIGITAL_Peca_generica_2_07072023.jpeg",
        "Kit todo dia, nutrição e inteligência corporal"
    ),
    Magazine(
        "https://static.natura.com.br/static/socialselling/07072023/COMPARTILHE_COM_O_ESPACO_DIGITAL_Peca_generica_2_07072023.jpeg",
        "Kit todo dia, nutrição e inteligência corporal"
    ),
)