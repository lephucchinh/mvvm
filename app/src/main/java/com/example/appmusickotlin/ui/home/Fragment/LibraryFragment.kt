package com.example.appmusickotlin.ui.home.Fragment

import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmusickotlin.ui.diaglogs.DialogAddLibraryFragment
import com.example.appmusickotlin.ui.adapter.MusicAdapter
import com.example.appmusickotlin.databinding.FragmentLibraryfragmentBinding
import com.example.appmusickotlin.model.Song
import com.example.appmusickotlin.util.callBack.OnEditButtonClickListener
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator




class LibraryFragment : Fragment(), OnEditButtonClickListener {

    private lateinit var binding: FragmentLibraryfragmentBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLibraryfragmentBinding.inflate(inflater, container, false)

        val listMusic =  getAllMusic()
        val adapter = MusicAdapter(this.context,listMusic,this,true,false)

        binding.recyclerView.layoutManager  = LinearLayoutManager(this.context)
        binding.recyclerView.adapter = adapter

        // THEM DATA VAO DANH SACH
        binding.btnLeft.setOnClickListener {
            binding.recyclerView.itemAnimator = SlideInLeftAnimator()
            val newItem = Song("1","them vao",200L , 0)
            adapter.addItem(newItem,1)

        }
        binding.btnRight.setOnClickListener {
            binding.recyclerView.itemAnimator = SlideInUpAnimator(OvershootInterpolator(1f))
            val positionToRemove = 0
            adapter.removeItem(positionToRemove)
        }

        return binding.root
    }



    private fun getAllMusic(): MutableList<Song> {


        val musicUriList = mutableListOf<Song>()


        val musicProjection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ARTIST
        )
        // Thêm điều kiện cho câu truy vấn
        val selection = "${MediaStore.Audio.Media.DURATION} >= ?"
        val selectionArgs = arrayOf("1000") // Thời lượng >= 1000ms (1 giây)


        val musicCursor = this.context?.contentResolver?.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            musicProjection,
            selection,
            selectionArgs,
            null
        )

        musicCursor?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            val albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)
            val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)



            while (cursor.moveToNext()) {
                val id = cursor.getString(idColumn)
                val title = cursor.getString(titleColumn)
                val albumId = cursor.getLong(albumIdColumn)
                val duration = cursor.getLong(durationColumn)
                val artist = cursor.getString(artistColumn)

                // Lấy ảnh album từ ID album

                val song = Song(id,title,duration,albumId,artist)

                musicUriList.add(song)

            }

        }

// Sắp xếp danh sách theo thứ tự alphabetic của name
        musicUriList.sortBy { it.name }

        return musicUriList
    }

    override fun onEditButtonClick(song: Song ) {

        val addDialog = DialogAddLibraryFragment()
        val args = Bundle()
        args.putSerializable("song", song) // Đặt đối tượng Song vào Bundle
        // Đặt Bundle vào DialogFragment
        addDialog.arguments = args

        addDialog.show(childFragmentManager,"Add Dialog")
    }

    override fun onDeleteButtonClick(song: Song, position: Int) {
    }


}