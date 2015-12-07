/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lsp.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alexander
 */
@Entity
@Table(name = "temp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Temp.findAll", query = "SELECT t FROM Temp t"),
    @NamedQuery(name = "Temp.findById", query = "SELECT t FROM Temp t WHERE t.id = :id"),
    @NamedQuery(name = "Temp.findByIp", query = "SELECT t FROM Temp t WHERE t.ip = :ip"),
    @NamedQuery(name = "Temp.findByLiveTo", query = "SELECT t FROM Temp t WHERE t.liveTo = :liveTo"),
    @NamedQuery(name = "Temp.findByForTable", query = "SELECT t FROM Temp t WHERE t.forTable = :forTable")})
public class Temp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "ip")
    private String ip;
    @Basic(optional = false)
    @Column(name = "liveTo")
    @Temporal(TemporalType.TIMESTAMP)
    private Date liveTo;
    @Basic(optional = false)
    @Column(name = "forTable")
    private int forTable;

    public Temp() {
    }

    public Temp(Integer id) {
        this.id = id;
    }

    public Temp(Integer id, String ip, Date liveTo, int forTable) {
        this.id = id;
        this.ip = ip;
        this.liveTo = liveTo;
        this.forTable = forTable;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getLiveTo() {
        return liveTo;
    }

    public void setLiveTo(Date liveTo) {
        this.liveTo = liveTo;
    }

    public int getForTable() {
        return forTable;
    }

    public void setForTable(int forTable) {
        this.forTable = forTable;
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
        if (!(object instanceof Temp)) {
            return false;
        }
        Temp other = (Temp) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lsp.entity.Temp[ id=" + id + " ]";
    }
    
}
