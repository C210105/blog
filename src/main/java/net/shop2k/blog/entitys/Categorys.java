package net.shop2k.blog.entitys;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 *カテゴリー情報 
 */

@Data //メソッドを自動的に生成定義
@Entity //DB内にマップに指定
@NoArgsConstructor //デフォルトのコンストラクタを自動的に生成、パラメータを取らず
// @AllArgsConstructor　//全てのフィルードを受け入れ
@Table(name = "categorys") //categorysテーブルをマップの指定
public class Categorys {
    
    @Id //主キー
    @GeneratedValue(strategy = GenerationType.IDENTITY) //主キー値を自動的に生成定義
    private Long id;

    @Column(name = "name") //カテゴリーの名
    private String name;

    @Column(name = "createDay")
    private LocalDateTime createDay; // 登録日
    
    @Column(name = "updateDay") // 更新日
    private LocalDateTime updateDay;

    /*
     * コンストラクタ定義
     * nameパラメータ使用
     * 
     */
    public Categorys(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY) //フィーチャクラス間の1対多関係を定義する
    private Set<Articles> articles;
}
