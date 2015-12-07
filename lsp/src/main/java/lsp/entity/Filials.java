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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "filials")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Filials.findAll", query = "SELECT f FROM Filials f"),
    @NamedQuery(name = "Filials.findById", query = "SELECT f FROM Filials f WHERE f.id = :id"),
    @NamedQuery(name = "Filials.findByHostName", query = "SELECT f FROM Filials f WHERE f.hostName = :hostName"),
    @NamedQuery(name = "Filials.findByAddress", query = "SELECT f FROM Filials f WHERE f.address = :address"),
    @NamedQuery(name = "Filials.findByPhone", query = "SELECT f FROM Filials f WHERE f.phone = :phone"),
    @NamedQuery(name = "Filials.findBySubnet", query = "SELECT f FROM Filials f WHERE f.subnet = :subnet"),
    @NamedQuery(name = "Filials.findByAuth", query = "SELECT f FROM Filials f WHERE f.auth = :auth"),
    @NamedQuery(name = "Filials.findByWanIP", query = "SELECT f FROM Filials f WHERE f.wanIP = :wanIP"),
    @NamedQuery(name = "Filials.findByCheckIP", query = "SELECT f FROM Filials f WHERE f.checkIP = :checkIP"),
    @NamedQuery(name = "Filials.findByCheckStat", query = "SELECT f FROM Filials f WHERE f.checkStat = :checkStat"),
    @NamedQuery(name = "Filials.findByLastPing", query = "SELECT f FROM Filials f WHERE f.lastPing = :lastPing")})
public class Filials implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "hostName")
    private String hostName;
    @Basic(optional = false)
    @Column(name = "address")
    private String address;
    @Basic(optional = false)
    @Column(name = "phone")
    private String phone;
    @Basic(optional = false)
    @Column(name = "subnet")
    private String subnet;
    @Basic(optional = false)
    @Column(name = "auth")
    private String auth;
    @Basic(optional = false)
    @Column(name = "wanIP")
    private String wanIP;
    @Basic(optional = false)
    @Column(name = "checkIP")
    private String checkIP;
    @Basic(optional = false)
    @Column(name = "checkStat")
    private int checkStat;
    @Column(name = "lastPing")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPing;
    @JoinColumn(name = "admin", referencedColumnName = "id")
    @ManyToOne
    private Users admin;
    @JoinColumn(name = "isp", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Isp isp;

    public Filials() {
    }

    public Filials(Integer id) {
        this.id = id;
    }

    public Filials(Integer id, String hostName, String address, String phone, String subnet, String auth, String wanIP, String checkIP, int checkStat) {
        this.id = id;
        this.hostName = hostName;
        this.address = address;
        this.phone = phone;
        this.subnet = subnet;
        this.auth = auth;
        this.wanIP = wanIP;
        this.checkIP = checkIP;
        this.checkStat = checkStat;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSubnet() {
        return subnet;
    }

    public void setSubnet(String subnet) {
        this.subnet = subnet;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getWanIP() {
        return wanIP;
    }

    public void setWanIP(String wanIP) {
        this.wanIP = wanIP;
    }

    public String getCheckIP() {
        return checkIP;
    }

    public void setCheckIP(String checkIP) {
        this.checkIP = checkIP;
    }

    public int getCheckStat() {
        return checkStat;
    }

    public void setCheckStat(int checkStat) {
        this.checkStat = checkStat;
    }

    public Date getLastPing() {
        return lastPing;
    }

    public void setLastPing(Date lastPing) {
        this.lastPing = lastPing;
    }

    public Users getAdmin() {
        return admin;
    }

    public void setAdmin(Users admin) {
        this.admin = admin;
    }

    public Isp getIsp() {
        return isp;
    }

    public void setIsp(Isp isp) {
        this.isp = isp;
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
        if (!(object instanceof Filials)) {
            return false;
        }
        Filials other = (Filials) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lsp.entity.Filials[ id=" + id + " ]";
    }
    
}
