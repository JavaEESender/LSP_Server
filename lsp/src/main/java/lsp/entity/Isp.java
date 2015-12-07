/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lsp.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Alexander
 */
@Entity
@Table(name = "isp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Isp.findAll", query = "SELECT i FROM Isp i"),
    @NamedQuery(name = "Isp.findById", query = "SELECT i FROM Isp i WHERE i.id = :id"),
    @NamedQuery(name = "Isp.findByName", query = "SELECT i FROM Isp i WHERE i.name = :name"),
    @NamedQuery(name = "Isp.findByPhone", query = "SELECT i FROM Isp i WHERE i.phone = :phone")})
public class Isp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "phone")
    private String phone;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "isp")
    private Collection<Filials> filialsCollection;

    public Isp() {
    }

    public Isp(Integer id) {
        this.id = id;
    }

    public Isp(Integer id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @XmlTransient
    public Collection<Filials> getFilialsCollection() {
        return filialsCollection;
    }

    public void setFilialsCollection(Collection<Filials> filialsCollection) {
        this.filialsCollection = filialsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Isp)) {
            return false;
        }
        Isp other = (Isp) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lsp.entity.Isp[ id=" + id + " ]";
    }
    
}
